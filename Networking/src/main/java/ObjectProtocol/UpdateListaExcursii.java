package ObjectProtocol;

import autogara.domain.Excursie;

import java.io.Serializable;
import java.util.ArrayList;

public class UpdateListaExcursii implements Response, Serializable {
    private ArrayList<Excursie> excursii;
    public UpdateListaExcursii(ArrayList<Excursie> excursii){
        this.excursii = excursii;
    }
    public ArrayList<Excursie> getExcursii(){
        return excursii;
    }
}
