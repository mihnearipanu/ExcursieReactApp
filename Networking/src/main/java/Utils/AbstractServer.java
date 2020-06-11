package Utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server = null;
    public AbstractServer(int port){
        this.port = port;
    }

    public void start() throws ServerException{
        try{
            server = new ServerSocket(port);
            while(true) {
                System.out.println("Astept clienti...");
                Socket client = server.accept();
                System.out.println("Client conectat! :)");
                processRequest(client);
            }
        }catch(IOException e){
            throw new ServerException("Eroare la start: ", e);
        }finally {
            stop();
        }
    }

    protected abstract void processRequest(Socket client);

    public void stop() throws ServerException {
        try {
            server.close();
        } catch (IOException e) {
            throw new ServerException("Eroare la inchidere: ", e);
        }
    }
}
