import autogara.domain.Bilet;
import autogara.domain.Excursie;
import autogara.domain.User;
import Observer.Observer;
import autogara.persistence.RepositoryBilet;
import autogara.persistence.RepositoryExcursie;
import autogara.persistence.RepositoryUser;
import Service.IService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Service implements IService {
    private RepositoryBilet repositoryBilet;
    private RepositoryExcursie repositoryExcursie;
    private RepositoryUser repositoryUser;
    private Map<String, Observer> clientiLogati;

    public Service(RepositoryBilet repositoryBilet, RepositoryExcursie repositoryExcursie, RepositoryUser repositoryUser){
        this.repositoryBilet = repositoryBilet;
        this.repositoryExcursie = repositoryExcursie;
        this.repositoryUser = repositoryUser;
        clientiLogati = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(User user, Observer observer) {
        if(verifica(user.getUsername(), user.getPassword())){
            clientiLogati.put(user.getUsername(), observer);
        }
    }

    @Override
    public synchronized void logout(String username) {
        clientiLogati.remove(username);
    }

    @Override
    public boolean verifica(String username, String password) {
        User user = repositoryUser.findByUserAndPass(username, password);
        return user != null;
    }

    @Override
    public ArrayList<Excursie> getAll() {
        return repositoryExcursie.findAll();
    }

    @Override
    public ArrayList<Excursie> getFiltrare(String obiectiv, LocalTime ora1, LocalTime ora2) {
        return repositoryExcursie.filtrare1(obiectiv, ora1, ora2);
    }

    @Override
    public void rezervare(Bilet bilet) {
        repositoryBilet.save(bilet);
        Excursie excursie = repositoryExcursie.findOne(bilet.getIdExcursie());
        excursie.setNrLocuri(excursie.getNrLocuri() - bilet.getNrLocuri());
        repositoryExcursie.update(excursie.getId(), excursie);
    }
}
