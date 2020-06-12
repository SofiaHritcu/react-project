package swimmingContest.network.dto;

import swimmingContest.model.Person;
import java.io.Serializable;

public class UserDTO extends Person implements Serializable {
    private String password;

    public UserDTO() {
    }

    public UserDTO(String firstName, String lastName, String password) {
        super(firstName, lastName);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "User: "+this.getFirstName()+" "+this.getLastName()+" "+this.password;
    }
}
