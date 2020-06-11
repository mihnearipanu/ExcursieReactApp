package ObjectProtocol;

import jdk.vm.ci.meta.Local;

import java.io.Serializable;
import java.time.LocalTime;

public class GetFiltrareRequest implements Request, Serializable {
    private String obiectiv;
    private LocalTime ora1;
    private LocalTime ora2;
    public GetFiltrareRequest(String obiectiv, LocalTime ora1, LocalTime ora2){
        this.obiectiv = obiectiv;
        this.ora1 = ora1;
        this.ora2 = ora2;
    }
    public String getObiectiv() {
        return obiectiv;
    }
    public LocalTime getOra1() {
        return ora1;
    }
    public LocalTime getOra2() {
        return ora2;
    }
}
