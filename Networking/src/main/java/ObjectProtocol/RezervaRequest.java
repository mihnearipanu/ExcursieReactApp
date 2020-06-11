package ObjectProtocol;

import autogara.domain.Bilet;

import java.io.Serializable;

public class RezervaRequest implements Request, Serializable {
    private Bilet bilet;
    public RezervaRequest(Bilet bilet){
        this.bilet = bilet;
    }
    public Bilet getBilet() {
        return bilet;
    }
}
