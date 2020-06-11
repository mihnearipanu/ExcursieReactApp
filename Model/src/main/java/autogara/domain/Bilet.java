package autogara.domain;

import java.io.Serializable;

public class Bilet extends Entity<String> implements Serializable {
    private String idExcursie;
    private String client;
    private Integer nrTel;

    public Integer getNrTel() {
        return nrTel;
    }

    public void setNrTel(Integer nrTel) {
        this.nrTel = nrTel;
    }

    private Integer nrLocuri;

    public Bilet(String id, String idExcursie, String client, Integer nrTel, Integer nrLocuri){
        super(id);
        this.idExcursie = idExcursie;
        this.client = client;
        this.nrTel = nrTel;
        this.nrLocuri = nrLocuri;
    }

    @Override
    public String getId() {
        return super.getId();
    }

    public String getIdExcursie() {
        return idExcursie;
    }

    public String getClient() {
        return client;
    }

    public Integer getNrLocuri() {
        return nrLocuri;
    }

    @Override
    public void setId(String s) {
        super.setId(s);
    }

    public void setIdExcursie(String idExcursie) {
        this.idExcursie = idExcursie;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setNrLocuri(Integer nrLocuri) {
        this.nrLocuri = nrLocuri;
    }
}
