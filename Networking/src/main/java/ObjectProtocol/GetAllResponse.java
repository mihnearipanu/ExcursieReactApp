package ObjectProtocol;

import autogara.domain.Excursie;

import java.io.Serializable;
import java.util.ArrayList;

public class GetAllResponse implements Response, Serializable {
    ArrayList<Excursie> excursii;
    public GetAllResponse(ArrayList<Excursie> excursii){
        this.excursii = excursii;
    }
    public ArrayList<Excursie> getAll(){
        return excursii;
    }
}
