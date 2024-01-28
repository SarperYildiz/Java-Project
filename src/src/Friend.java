import java.util.ArrayList;
import java.util.Date;

public class Friend extends User {
    ArrayList friendList;
    ArrayList blockedFriends;
    ArrayList blockedUsers;


    @Override
    public String toString() {
        return "Friend{" +
                "friendList=" + friendList +
                ", blockedFriends=" + blockedFriends +
                ", blockedUsers=" + blockedUsers +
                ", userID=" + userID +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", birthDate=" + birthDate +
                ", highschool='" + highschool + '\'' +
                '}';
    }

    public Friend(int userID, String name, String username, String password, Date birthDate, String highschool, ArrayList friendList, ArrayList blockedFriends, ArrayList blockedUsers) {
        super(userID, name, username, password, birthDate, highschool);
        this.friendList=friendList;
        this.blockedFriends=blockedFriends;
        this.blockedUsers=blockedUsers;

    }
}

