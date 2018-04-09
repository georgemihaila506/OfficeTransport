package Domain;

import java.io.Serializable;

public class Users implements Serializable {
    private String username;
    private String password;

    public Users(String username, String password) {
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
