package ObjectProtocol;

import java.io.Serializable;

public class LogoutRequest implements Request, Serializable {
    String username;
    public LogoutRequest(String username){
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
}
