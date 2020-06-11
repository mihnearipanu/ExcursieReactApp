package ObjectProtocol;

import autogara.domain.Bilet;
import autogara.domain.Excursie;
import autogara.domain.User;
import Observer.Observer;
import Service.IService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServiceProxy implements IService {
    private String host;
    private Integer port;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Socket connection;
    private Observer client;
    private BlockingQueue<Response> responses;
    private volatile boolean finished;

    public ServiceProxy(String host, int port){
        this.host = host;
        this.port = port;
        responses = new LinkedBlockingQueue<>();
    }

    @Override
    public void login(User user, Observer observer) throws Exception {
        initConnection();
        sendRequest(new LoginRequest(user));
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse errorResponse = (ErrorResponse) response;
            throw new Exception(errorResponse.getError());
        }
    }

    @Override
    public void logout(String username) {
        if (connection != null){
            sendRequest(new LogoutRequest(username));
            System.out.println("La revedere " + username + "!");
            Response response = readResponse();
            closeConnection();
        }
    }

    @Override
    public boolean verifica(String username, String password) {
        sendRequest(new VerificaRequest(username, password));
        Response response = readResponse();
        return !(response instanceof ErrorResponse);
    }

    private void handleUpdate(Response response){
        if(response instanceof UpdateListaExcursii){
            UpdateListaExcursii response1 = (UpdateListaExcursii) response;
            ArrayList<Excursie> excursii = response1.getExcursii();
            client.updateExcursii(excursii);
        }
    }

    @Override
    public ArrayList<Excursie> getAll() throws Exception {
        sendRequest(new GetAllRequest());
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            throw new Exception(((ErrorResponse)response).getError());
        }
        GetAllResponse response1 = (GetAllResponse) response;
        return response1.getAll();
    }

    @Override
    public ArrayList<Excursie> getFiltrare(String obiectiv, LocalTime ora1, LocalTime ora2) throws Exception {
        sendRequest(new GetFiltrareRequest(obiectiv, ora1, ora2));
        Response response = readResponse();
        if(response instanceof ErrorResponse){
            throw new Exception(((ErrorResponse)response).getError());
        }
        GetFiltrareResponse response1 = (GetFiltrareResponse) response;
        return response1.getAll();
    }

    @Override
    public void rezervare(Bilet bilet) {
        initConnection();
        sendRequest(new RezervaRequest(bilet));
        Response response = readResponse();
        if(response instanceof OkResponse){
            System.out.println("Bilet salvat");
        }
        else{
            System.out.println("Biletul nu a putut fi adaugat");
        }
    }

    private void startReader(){
        Thread thread = new Thread(new ReaderThread());
        thread.start();
    }

    private void initConnection(){
        try{
            connection = new Socket(host, port);
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void closeConnection(){
        finished = true;
        try{
            inputStream.close();
            outputStream.close();
            connection.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void sendRequest(Request request){
        try{
            outputStream.writeObject(request);
            outputStream.flush();
        }catch(IOException e){
            System.out.println("Eroare la send: " + e.getMessage());
        }
    }

    private Response readResponse(){
        Response response = null;
        try{
            response = responses.take();
        }catch(InterruptedException e){
            System.out.println("Eroare la read: " + e.getMessage());
        }
        return response;
    }

    private class ReaderThread implements Runnable{
        public void run(){
            while(!finished){
                try{
                    Object response = inputStream.readObject();
                    System.out.println("Am primit raspunsul");
                    if(response instanceof UpdateListaExcursii){
                        handleUpdate((UpdateListaExcursii)response);
                    }
                    else{
                        try{
                            responses.put((Response) response);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }catch (IOException | ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
