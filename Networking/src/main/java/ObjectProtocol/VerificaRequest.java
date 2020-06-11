package ObjectProtocol;

import java.io.Serializable;

public class VerificaRequest implements Request, Serializable {
    private String username;
    private String password;

    public VerificaRequest(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
