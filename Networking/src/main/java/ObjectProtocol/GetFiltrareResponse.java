package ObjectProtocol;

import autogara.domain.Excursie;

import java.io.Serializable;
import java.util.ArrayList;

public class GetFiltrareResponse implements Response, Serializable {
    ArrayList<Excursie> excursii;
    public GetFiltrareResponse(ArrayList<Excursie> excursii){
        this.excursii = excursii;
    }
    public ArrayList<Excursie> getAll(){
        return excursii;
    }
}
