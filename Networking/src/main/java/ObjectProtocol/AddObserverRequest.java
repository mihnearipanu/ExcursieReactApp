package ObjectProtocol;

import java.io.Serializable;

public class AddObserverRequest implements Serializable {
    private String user;
    public String getUser() {
        return user;
    }
    public AddObserverRequest(String u){
        user = u;
    }
}
