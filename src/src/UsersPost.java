import java.util.ArrayList;
import java.util.Date;

public class UsersPost extends Friend {
ArrayList textOfPostLists;
ArrayList imageOfPostLists;
ArrayList videoOfPostLists;
ArrayList allPosts;


    public UsersPost(int userID, String name, String username, String password, Date birthDate, String highschool, ArrayList friendList,ArrayList blockedFriends,ArrayList blockedUsers, ArrayList textOfPostLists, ArrayList imageOfPostLists, ArrayList videoOfPostLists,ArrayList allPosts) {
        super(userID, name, username, password, birthDate, highschool, friendList,blockedFriends,blockedUsers);
        this.textOfPostLists = textOfPostLists;
        this.imageOfPostLists = imageOfPostLists;
        this.videoOfPostLists = videoOfPostLists;
        this.allPosts=allPosts;
    }

    @Override
    public String toString() {
        return "UsersPost{" +
                "textOfPostLists=" + textOfPostLists +
                ", imageOfPostLists=" + imageOfPostLists +
                ", videoOfPostLists=" + videoOfPostLists +
                ", allPosts=" + allPosts +
                '}';
    }
}
