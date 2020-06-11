package Service;

import autogara.domain.Bilet;
import autogara.domain.Excursie;
import autogara.domain.User;
import Observer.Observer;

import java.time.LocalTime;
import java.util.ArrayList;

public interface IService {
    void login(User user, Observer observer) throws Exception;
    void logout(String username);
    boolean verifica(String username, String password);
    ArrayList<Excursie> getAll() throws Exception;
    ArrayList<Excursie> getFiltrare(String obiectiv, LocalTime ora1, LocalTime ora2) throws Exception;
    void rezervare(Bilet bilet);
}
