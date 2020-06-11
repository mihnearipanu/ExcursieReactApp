package ObjectProtocol;

import autogara.domain.User;

import java.io.Serializable;

public class LoginRequest implements Request, Serializable {
    private User user;
    public LoginRequest(User user){
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
