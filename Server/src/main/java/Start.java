import autogara.persistence.RepoBilet;
import autogara.persistence.RepoExcursie;
import autogara.persistence.RepoUser;
import Service.IService;
import Utils.AbstractServer;
import Utils.ObjectConcurrentServer;

import java.io.FileReader;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.Properties;

public class Start {
    private static Properties getProperty(){
        Properties serverProperty = new Properties();
        try{
            serverProperty.load(new FileReader("db.properties"));
            System.out.println("Proprietati gasite!");
            serverProperty.list(System.out);
        }catch(IOException e){
            System.out.println("Proprietatile nu au fost gasite: " + e.getMessage());
            return null;
        }
        return serverProperty;
    }

    public static void main(String[] args){
        RepoUser repositoryUser = new RepoUser("Useri");
        RepoExcursie repositoryExcursie = new RepoExcursie("Excursii");
        RepoBilet repositoryBilet = new RepoBilet("Bilet");
        IService serv = new Service(repositoryBilet, repositoryExcursie, repositoryUser);
        AbstractServer server = new ObjectConcurrentServer(55555, serv);
        try{
            server.start();
        }catch(ServerException e){
            System.out.println("Nu am putut deschide serverul: " + e.getMessage());
        }
    }
}
