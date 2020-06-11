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
import java.util.ArrayList;

public class ClientObjectWorker implements Runnable, Observer {
    private IService service;
    private Socket connection;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private volatile boolean connected;

    public ClientObjectWorker(IService serv, Socket connection){
        this.service = serv;
        this.connection = connection;
        this.connected = true;
        try{
            outputStream = new ObjectOutputStream(this.connection.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(this.connection.getInputStream());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(connected){
            try{
                Object request = inputStream.readObject();
                System.out.println("Am citit");
                Response response = handleRequest((Request) request);
                if(response != null){
                    sendResponse(response);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try{
            inputStream.close();
            outputStream.close();
            connection.close();
        } catch (IOException e){
            System.out.println("Eroare " + e);
        }
    }

    @Override
    public void updateExcursii(ArrayList<Excursie> excursii) {
        try{
            sendResponse(new GetAllResponse(excursii));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Response handleRequest(Request request){
        Response response = null;

        if(request instanceof LoginRequest){
            System.out.println("Login Request");
            User user = ((LoginRequest)request).getUser();
            try{
                service.login(user, this);
                return new OkResponse();
            }catch(Exception e){
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof LogoutRequest){
            System.out.println("Logout Request");
            String user = ((LogoutRequest)request).getUsername();
            try{
                service.logout(user);
                connected = false;
                return new OkResponse();
            }catch(Exception ignored){

            }
        }

        if(request instanceof GetAllRequest){
            System.out.println("Get All Request");
            try{
                ArrayList<Excursie> excursii = service.getAll();
                return new GetAllResponse(excursii);
            }catch (Exception e){
                return new ErrorResponse(e.getMessage());
            }
        }

        if(request instanceof GetFiltrareRequest){
            System.out.println("Get Filtrare Request");
            try{
                ArrayList<Excursie> excursii = service.getFiltrare(((GetFiltrareRequest) request).getObiectiv(),
                        ((GetFiltrareRequest) request).getOra1(),
                        ((GetFiltrareRequest) request).getOra2());
                return new GetFiltrareResponse(excursii);
            }catch (Exception e){
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof RezervaRequest){
            System.out.println("Rezerva Request");
            Bilet bilet = ((RezervaRequest) request).getBilet();
            service.rezervare(bilet);
            return new OkResponse();
        }

        if(request instanceof VerificaRequest){
            System.out.println("Verifica Request");
            if(service.verifica(((VerificaRequest) request).getUsername(),
                    ((VerificaRequest) request).getPassword())){
                return new OkResponse();
            }
            else return new ErrorResponse("404: User not found");
        }

        return null;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("Trimit raspuns...");
        outputStream.writeObject(response);
        outputStream.flush();
    }
}
