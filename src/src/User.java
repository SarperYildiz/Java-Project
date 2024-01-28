import java.util.Date;

public class User {
    public int userID;
    public String name;



    public String username;


    private String password;
    public Date birthDate;
    public String highschool;
    public User(int userID, String name, String username, String password, Date birthDate, String highschool) {
        this.userID = userID;
        this.name = name;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.highschool = highschool;
    }
    public String getPassword() {

        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthDate=" + birthDate +
                ", highschool='" + highschool + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

}
