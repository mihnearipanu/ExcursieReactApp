package autogara.persistence;

import autogara.domain.Excursie;

import java.time.LocalTime;
import java.util.ArrayList;

public interface RepositoryExcursie extends IRepository<String, Excursie> {
    ArrayList<Excursie> filtrare1(String Obiectiv, LocalTime ora1, LocalTime ora2);
}
