package autogara.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalTime;

@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")

public class Excursie extends Entity<String> implements Serializable {
    private String numeCompTrans;
    private String obiectiv;
    private LocalTime oraPlecarii;
    private Double pret;
    private Integer nrLocuri;

    public Excursie(){
        super("");
    }

    public Excursie(String id, String numeCompTrans, String obiectiv, LocalTime oraPlecarii, Double pret, Integer nrLocuri){
        super(id);
        this.numeCompTrans = numeCompTrans;
        this.obiectiv = obiectiv;
        this.oraPlecarii = oraPlecarii;
        this.pret = pret;
        this.nrLocuri = nrLocuri;
    }

    @Override
    public String getId() {
        return super.getId();
    }

    public String getNumeCompTrans() {
        return numeCompTrans;
    }

    public String getObiectiv() {
        return obiectiv;
    }

    public LocalTime getOraPlecarii() {
        return oraPlecarii;
    }

    public Double getPret() {
        return pret;
    }

    public Integer getNrLocuri() {
        return nrLocuri;
    }

    @Override
    public void setId(String s) {
        super.setId(s);
    }

    public void setNumeCompTrans(String numeCompTrans) {
        this.numeCompTrans = numeCompTrans;
    }

    public void setObiectiv(String obiectiv) {
        this.obiectiv = obiectiv;
    }

    public void setOraPlecarii(LocalTime oraPlecarii) {
        this.oraPlecarii = oraPlecarii;
    }

    public void setPret(Double pret) {
        this.pret = pret;
    }

    public void setNrLocuri(Integer nrLocuri) {
        this.nrLocuri = nrLocuri;
    }
}
