package swimmingContest.model;

import java.io.Serializable;
import java.util.Objects;

/***
 * The User class extends the Person class and represents the persons who are using effectively the application
 */
public class User extends Person implements Serializable {
    private String password;

    public User() {
    }

    /***
     * A User is differentiated from a simple Person by his password
     * @param firstName - the firstName of the User
     * @param lastName - the lastName of the User
     * @param password - the password that is used by the User to log into the app
     */
    public User(String firstName, String lastName, String password) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), password);
    }

    @Override
    public String toString() {
        return "User: "+this.getFirstName()+" "+this.getLastName()+" "+this.password;
    }
}
