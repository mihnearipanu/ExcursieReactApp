package autogara.domain;

import java.io.Serializable;

public class User extends Entity<String> implements Serializable {
    private String username;
    private String password;

    public User(String id, String username, String password){
        super(id);
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
