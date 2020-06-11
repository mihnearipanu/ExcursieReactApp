package ObjectProtocol;

import autogara.domain.User;

import java.io.Serializable;

public class VerificaResponse implements Response, Serializable {
    private User user;

    public VerificaResponse(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
