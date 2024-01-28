import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class mySocialBook {
    public static ArrayList<User> Users= new ArrayList<User>();
    public static ArrayList<Friend>Friends=new ArrayList<>();
    public static ArrayList<UsersPost>UsersPosts=new ArrayList<>();

    public static boolean signin=false;
    public static int tempvalue;
    public static User place;
    public static int userID = 1;
    public static int index;

    public static void main(String[] args) throws IOException, ParseException {


        String commandss;

        File file3 = new File("commands.txt");
        BufferedReader readerrr = null;
        readerrr = new BufferedReader(new FileReader(file3));
        BufferedReader br = new BufferedReader(readerrr);


        while ((commandss = br.readLine()) != null) {
            UserReading valuesoftxt = new UserReading();                                                                //read commands.txt and assign to value
            String[] islem = commandss.split("\t");




            while (tempvalue + 1 <= valuesoftxt.getLengthofarray() * 5) {


                String name = valuesoftxt.getFeature().get(tempvalue);
                String username = valuesoftxt.getFeature().get(tempvalue + 1);
                String password = valuesoftxt.getFeature().get(tempvalue + 2);
                String birthDate = valuesoftxt.getFeature().get(tempvalue + 3);
                String highschool = valuesoftxt.getFeature().get(tempvalue + 4);                                        //determining the user's information by pulling from the arraylist and throw the information createuser method
                Date birthDate1 = new SimpleDateFormat("MM/dd/yyyy").parse(birthDate);
                createUser(userID, name, username, password, birthDate1, highschool);
                userID++;

                tempvalue += 5;
            }




            if (islem[0].equals("ADDUSER")) {                                                                               // We add users to the users.txt file in the adduser command
                System.out.println("------------------------" + "\n" + "commands:" + commandss);
                try (FileWriter fw = new FileWriter("users.txt", true);
                     BufferedWriter bw = new BufferedWriter(fw);
                     PrintWriter out = new PrintWriter(bw)) {
                    out.println(islem[1] + "\t" + islem[2] + "\t" + islem[3] + "\t" + islem[4] + "\t" + islem[5]);


                } catch (IOException e) {

                }
                System.out.println(islem[1] + " has been successfully added");
            }

            if (islem[0].equals("SIGNIN")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);

                for (int i = 0; i < valuesoftxt.getLengthofarray(); i++) {


                    if (islem[1].equals(Users.get(i).username) && islem[2].equals(Users.get(i).getPassword())) {
                        System.out.println("You have successfully signed in.");
                        signin = true;                                                                                  // We assign a boolean named signin so that we can use it when logging in.
                        index = i;
                        place = Users.get(i);                                                                           // we set a place value to use the information of the person who is currently logged in

                        ArrayList<String> friendlisttt = new ArrayList<>();
                        ArrayList<String> textofpostssss = new ArrayList<>();
                        ArrayList<String> imageofpostssss = new ArrayList<>();
                        ArrayList<String> videoOfpostssss = new ArrayList<>();
                        ArrayList<String> allPostssss = new ArrayList<>();
                        ArrayList<String> blockedFriendss = new ArrayList<>();
                        ArrayList<String> blockedUserss = new ArrayList<>();
                        createPost(place.userID, place.name, place.username, place.getPassword(), place.birthDate, place.highschool, friendlisttt, blockedFriendss,blockedUserss, textofpostssss, imageofpostssss, videoOfpostssss, allPostssss);
                        createFriends(place.userID, place.name, place.username, place.getPassword(), place.birthDate, place.highschool, friendlisttt, blockedFriendss,blockedUserss);

                        break;

                    }


                }
                if (!signin) {
                    System.out.println("Invalid username or password! Please try again");

                }

            } else if (islem[0].equals("LISTUSERS")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);

                if (signin) {

                    for (int i = 0; i < valuesoftxt.getLengthofarray(); i++) {

                        if(!Users.get(i).name.equals("")) {
                            DateFormat dateFormat3 = new SimpleDateFormat("MM/dd/yyyy");                          //we use this code to write the date in the desired format
                            String birthDate3 = dateFormat3.format(Users.get(i).birthDate);
                            System.out.println("Name:" + Users.get(i).name + "\n" + "username:" + Users.get(i).username + "\n" + "birthday:" + birthDate3 + "\n" + "school:" + Users.get(i).highschool + "\n" + "----------------------");
                        }
                        }
                } else {
                    System.out.println("Error: Please sign in and try again.");
                }
            } else if (islem[0].equals("UPDATEPROFILE")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);
                if (signin) {

                    for (int i = 0; i < valuesoftxt.getLengthofarray(); i++) {

                        BufferedReader file = new BufferedReader(new FileReader("users.txt"));
                        String line;
                        String input = "";
                        String newName = islem[1];
                        String newDate = islem[2];
                        String newSchool = islem[3];

                        while ((line = file.readLine()) != null)
                            input += line + "\n";

                        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        String birthDate = dateFormat.format(place.birthDate);

                        input = input.replace(place.name, newName);
                        input = input.replace(birthDate, newDate);                                                       //replacing the new values entered by the user with the old ones and updating.
                        input = input.replace(place.highschool, newSchool);


                        FileOutputStream os = new FileOutputStream("users.txt");
                        os.write(input.getBytes());


                        file.close();
                        os.close();
                        Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(newDate);
                        place.name = newName;
                        place.birthDate = date1;
                        place.highschool = newSchool;
                        System.out.println("Your user profile has been successfully updated");
                        break;


                    }
                } else {
                    System.out.println("Please sign in and try again.");
                }

            } else if (islem[0].equals("CHPASS")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);
                if (signin) {
                    if (!islem[1].equals(place.getPassword())) {
                        System.out.println("Password missmatch");
                    } else {
                        BufferedReader file = new BufferedReader(new FileReader("users.txt"));
                        String line;
                        String input = "";

                        String newPass = islem[2];


                        while ((line = file.readLine()) != null)
                            input += line + "\n";                                                                       // replacing user-entered information with the replace command.

                        input = input.replace(place.getPassword(), newPass);


                        FileOutputStream os = new FileOutputStream("users.txt");
                        os.write(input.getBytes());

                        file.close();
                        os.close();
                        place.setPassword(newPass);                                                                     // updating the current user's password using setter because we set the password again.


                    }
                } else {
                    System.out.println("Please sign in and try again");
                }

            } else if (islem[0].equals("REMOVEUSER")) {

                int p=Integer.parseInt(islem[1]);


                Users.get(p-1).name="";
                Users.get(p-1).username="";
                Users.get(p-1).birthDate=null;
                Users.get(p-1).highschool="";

                System.out.println("------------------------" + "\n" + "commands:" + commandss);

                File fileToBeModified = new File("users.txt");

                String input1 = "";

                BufferedReader rdr = null;

                FileWriter writer = null;

                try {

                    int n = Integer.parseInt(islem[1]);

                    String oldline = Files.readAllLines(Paths.get("users.txt")).get(n - 1);

                    rdr = new BufferedReader(new FileReader(fileToBeModified));

                    //Reading all the lines of input text file into old content

                    String line = rdr.readLine();

                    while (line != null) {
                        input1 = input1 + line + System.lineSeparator();

                        line = rdr.readLine();
                    }

                                                                                                                        //Replacing old string with new string in the old content

                    String newContent1 = input1.replaceAll(oldline, "*\t*\t*\t??/??/????\t*");


                                                                                                                        //Rewriting the input text file with new content

                    writer = new FileWriter(fileToBeModified);

                    writer.write(newContent1);

                    rdr.close();

                    writer.close();


                    System.out.println("User has been successfully removed.\n------------------------------------");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {


                        rdr.close();

                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            } else if (islem[0].equals("ADDFRIEND")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);
                if (signin) {
                    for (int i = 0; i < Friends.size(); i++) {
                        if (Friends.get(i).userID == place.userID) {
                            if (valuesoftxt.getFeature().contains(islem[1])) { // If there is a string entered by the person in the arraylist with the information of the users, do this operation.
                                if (Friends.get(i).friendList.contains(islem[1])) {                                     // add friends depending on whether the user is in the friend list or not
                                    System.out.println("This user is already in your friend list!");
                                } else {
                                    Friends.get(i).friendList.add(islem[1]);
                                    System.out.println(islem[1] + " has been successfully added to your friend list");
                                }
                            } else {
                                System.out.println("No such user");
                            }

                        }

                    }
                } else {
                    System.out.println("Please sign in and try again.");
                }

            } else if (islem[0].equals("REMOVEFRIEND")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);
                if (signin) {
                    for (int i = 0; i < Friends.size(); i++) {
                        if (Friends.get(i).userID == place.userID) {
                            if (valuesoftxt.getFeature().contains(islem[1])) {
                                if (Friends.get(i).friendList.contains(islem[1])) {
                                    Friends.get(i).friendList.remove(islem[1]);
                                    System.out.println(islem[1] + " has been successfully removed to your friend list");
                                } else {                                                                                    //Remove friends based on whether the user is in the friend list or not
                                    System.out.println("no such friend");
                                }
                            } else {
                                System.out.println("no such user");
                            }
                        }
                    }
                } else {
                    System.out.println("Please sign in and try again.");
                }
            } else if (islem[0].equals("LISTFRIENDS")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);

                if (signin) {


                    for (int i = 0; i < Friends.size(); i++) {                                                             // We use it when printing by specifying the index from the list of user information.

                        if (Friends.get(i).userID == place.userID) {
                            if (Friends.get(i).friendList.size() != 0){
                                for (int y = 0; y < Friends.get(i).friendList.size(); y++) {
                                    int x = valuesoftxt.getFeature().indexOf(Friends.get(i).friendList.get(y));
                                    if(x-1>=0)
                                    System.out.println("Name:" + valuesoftxt.getFeature().get(x - 1) + "\n" + "username:" + valuesoftxt.getFeature().get(x) + "\n" + "birthday:" + valuesoftxt.getFeature().get(x + 2) + "\n" + "school:" + valuesoftxt.getFeature().get(x + 3) + "\n" + "----------------------");
                                }
                            }else{
                                System.out.println("You have not added any friend yet!");
                            }
                        }

                    }


                } else {
                    System.out.println("Error: Please sign in and try again.");
                }
            } else if (islem[0].equals("ADDPOST-TEXT")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);
                if (signin) {
                    for (int i = 0; i < UsersPosts.size(); i++) {
                        if (UsersPosts.get(i).userID == place.userID) {
                            Date d1 = new Date();
                            DateFormat d2 = new SimpleDateFormat("MM/dd/yyyy");
                            UsersPosts.get(i).allPosts.add(islem[1] + "\t" + islem[2] + "\t" + islem[3] + "\t" + islem[4] + "\t" + "?" + "\t" + "?" + "\t" + d2.format(d1)); // I put the question mark in the showpost command to determine if it is text.
                            String[] taggedFriends = islem[4].split(":");                     // separation by colon to change the spelling of the people tagged.
                            for (int x = 0; x < taggedFriends.length; x++) {
                                if (UsersPosts.get(i).friendList.contains(taggedFriends[x])) {


                                } else {
                                    System.out.println(taggedFriends[x] + " is not your friend, and will not be tagged!");
                                }
                            }


                        }
                        System.out.println("The post has been succesfully added.");
                    }
                } else {
                    System.out.println("Please sign in and try again");
                }


            } else if (islem[0].equals("ADDPOST-IMAGE")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);
                if (signin) {
                    for (int i = 0; i < UsersPosts.size(); i++) {
                        if (UsersPosts.get(i).userID == place.userID) {
                            Date d1 = new Date();                                                                       // I found such a solution to print the date as date.
                            DateFormat d2 = new SimpleDateFormat("MM/dd/yyyy");
                            UsersPosts.get(i).allPosts.add(islem[1] + "\t" + islem[2] + "\t" + islem[3] + "\t" + islem[4] + "\t" + islem[5] + "\t" + islem[6] + "\t" + d2.format(d1));
                            String[] taggedFriends = islem[4].split(":");
                            for (int x = 0; x < taggedFriends.length; x++) {
                                if (UsersPosts.get(i).friendList.contains(taggedFriends[x])) {
                                    String taggedFriends2 = "";
                                    taggedFriends2 = taggedFriends2 + taggedFriends[x];

                                } else {
                                    System.out.println(taggedFriends[x] + " is not your friend, and will not be tagged!");
                                }
                            }


                        }
                        System.out.println("The post has been succesfully added.");
                    }
                } else {
                    System.out.println("Please sign in and try again.");
                }

            } else if (islem[0].equals("ADDPOST-VIDEO")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);
                if (signin) {
                    for (int i = 0; i < UsersPosts.size(); i++) {
                        if (UsersPosts.get(i).userID == place.userID) {              // I made the 6th index of the operation value entered by the user double and it would give an error if the video duration is longer than 10 minutes.
                            if(Double.parseDouble(islem[6])>10.0){
                                System.out.println("Error: Your video exceeds maximum allowed duration of 10 minutes");

                            }
                            else {
                                Date d1 = new Date();
                                DateFormat d2 = new SimpleDateFormat("MM/dd/yyyy");
                                UsersPosts.get(i).allPosts.add(islem[1] + "\t" + islem[2] + "\t" + islem[3] + "\t" + islem[4] + "\t" + islem[5] + "\t" + Double.parseDouble(islem[6]) + "\t" + d2.format(d1));
                                String[] taggedFriends = islem[4].split(":");
                                for (int x = 0; x < taggedFriends.length; x++) {
                                    if (UsersPosts.get(i).friendList.contains(taggedFriends[x])) {
                                        String taggedFriends2 = "";
                                        taggedFriends2 = taggedFriends2 + taggedFriends[x];


                                    } else {
                                        System.out.println(taggedFriends[x] + " is not your friend, and will not be tagged!");
                                    }
                                }
                            }

                        }
                        System.out.println("The post has been succesfully added.");

                    }
                } else {
                    System.out.println("Please sign in and try again.");
                }

            } else if (islem[0].equals("REMOVELASTPOST")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);

                if (signin) {
                    for (int i = 0; i < UsersPosts.size(); i++) {
                        if(UsersPosts.get(i).allPosts.size()!=0){
                        if (UsersPosts.get(i).userID == place.userID) {
                            int postsize = UsersPosts.get(i).allPosts.size() - 1;                                       // we remove the last post by taking the postsize index
                            UsersPosts.get(i).allPosts.remove(postsize);
                            System.out.println("Your last post has been successfully removed");

                        }

                        }else{
                            System.out.println("Error: You do not have any post");
                        }
                    }
                }
                    else {
                    System.out.println("Please sign in and try again.");
                }
            }

            else if (islem[0].equals("SHOWPOSTS")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);
                System.out.println("***************" + "\n" + islem[1] + "'s posts" + "\n" + "***************");
                if (signin) {
                    for (int i = 0; i < UsersPosts.size(); i++) {
                        if (UsersPosts.get(i).allPosts.size() != 0){
                            if (Friends.get(i).userID == place.userID) {
                                int postsize = UsersPosts.get(i).allPosts.size();
                                ArrayList<String> post = new ArrayList<>();
                                for (int k = 0; k < postsize; k++) {
                                    String[] postt;
                                    postt = UsersPosts.get(i).allPosts.get(k).toString().split("\t");
                                    for (int s = 0; s < postt.length; s++) {
                                        post.add(String.valueOf(postt[s]));
                                    }

                                }
                                int lenthh = post.size();
                                for (int v = 0; v < lenthh; v++) {
                                    if (post.get(v + 5).equals("?")) {                                                      // for each post, if the 5th of the information entered by the user is a question mark, it is a text pos
                                        String[] taggedFriends = post.get((v + 5) - 2).split(":");
                                        String taggedFriends2 = "";
                                        List<String> taggedfriendslist = Arrays.asList(taggedFriends);
                                        for (int z = 0; z < taggedfriendslist.size(); z++) {
                                            for (int t = 0; t < userID - 1; t++) {
                                                if (taggedfriendslist.get(z).equals(Users.get(t).username)) {
                                                    taggedFriends2 += Users.get(t).name + " ";

                                                }
                                            }

                                        }
                                        if (taggedFriends2.equals("")) {
                                            System.out.println(post.get((v + 6) - 6) + "\n" + "Date:" + post.get(v + 6) + "\n" + "Location:" + post.get((v + 6) - 5) + "," + post.get((v + 6) - 4) + "\n" + "------------------------");
                                        } else {
                                            System.out.println(post.get((v + 6) - 6) + "\n" + "Date:" + post.get(v + 6) + "\n" + "Location:" + post.get((v + 6) - 5) + "," + post.get((v + 6) - 4) + "\n" + "Friends tagged in this post:" + taggedFriends2 + "\n" + "------------------------");
                                        }
                                    } else if (post.get(v + 5).contains(".")) {                                             // for each post, if the 5th of the information entered by the user is a question mark, it is a text post
                                        String[] taggedFriends3 = post.get((v + 5) - 2).split(":");
                                        String taggedFriends4 = "";
                                        List<String> taggedfriendslist = Arrays.asList(taggedFriends3);
                                        for (int z = 0; z < taggedfriendslist.size(); z++) {
                                            for (int t = 0; t < userID - 1; t++) {
                                                if (taggedfriendslist.get(z).equals(Users.get(t).username)) {
                                                    taggedFriends4 += Users.get(t).name + " ";
                                                }
                                            }

                                        }
                                        if (taggedFriends4.equals("")) {                                                      // if there is no tagged person, don't write after location
                                            System.out.println(post.get((v + 6) - 6) + "\n" + "Date:" + post.get(v + 6) + "\n" + "Location:" + post.get((v + 6) - 5) + "," + post.get((v + 6) - 4) + "\n" + "------------------------");
                                        } else {
                                            System.out.println(post.get((v + 6) - 6) + "\n" + "Date:" + post.get(v + 6) + "\n" + "Location:" + post.get((v + 6) - 5) + "," + post.get((v + 6) - 4) + "\n" + "Friends tagged in this post:" + taggedFriends4 + "\n" + "------------------------");
                                        }


                                    } else {
                                        String[] taggedFriends5 = post.get((v + 5) - 2).split(":");
                                        String taggedFriends6 = "";
                                        List<String> taggedfriendslist = Arrays.asList(taggedFriends5);
                                        for (int z = 0; z < taggedfriendslist.size(); z++) {
                                            for (int t = 0; t < userID - 1; t++) {
                                                if (taggedfriendslist.get(z).equals(Users.get(t).username)) {
                                                    taggedFriends6 += Users.get(t).name + " ";
                                                }
                                            }

                                        }
                                        if (taggedFriends6.equals("")) {                                                      // if there is no tagged person, don't write after location
                                            System.out.println(post.get((v + 6) - 6) + "\n" + "Date:" + post.get(v + 6) + "\n" + "Location:" + post.get((v + 6) - 5) + "," + post.get((v + 6) - 4) + "\n" + "------------------------");
                                        } else {
                                            System.out.println(post.get((v + 6) - 6) + "\n" + "Date:" + post.get(v + 6) + "\n" + "Location:" + post.get((v + 6) - 5) + "," + post.get((v + 6) - 4) + "\n" + "Friends tagged in this post:" + taggedFriends6 + "\n" + "------------------------");
                                        }


                                    }
                                    v += 6;
                                }


                            }
                    }else{
                            System.out.println(islem[1]+" does not have any posts yet.");
                        }
                    }
                } else {
                    System.out.println("Please sign in and try again.");
                }

            } else if (islem[0].equals("BLOCK")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);
                if (signin) {
                    for (int i = 0; i < Friends.size(); i++) {
                        if (Friends.get(i).userID == place.userID) {
                            if (valuesoftxt.getFeature().contains(islem[1])) {
                                Friends.get(i).blockedUsers.add(islem[1]);                                              // block the user in islem[1] and add them to the blocked friends list if they are a friend
                                if (Friends.get(i).friendList.contains(islem[1])) {
                                    Friends.get(i).blockedFriends.add(islem[1]);
                                    System.out.println(islem[1] + " has been successfully blocked");
                                }
                            } else {
                                System.out.println("No such user");
                            }

                        }
                    }
                } else {
                    System.out.println("Please sign in and try again.");

                }

            } else if (islem[0].equals("UNBLOCK")) {

                System.out.println("------------------------" + "\n" + "commands:" + commandss);
                if (signin) {
                    for (int i = 0; i < Friends.size(); i++) {
                        if (Friends.get(i).userID == place.userID) {
                            if (valuesoftxt.getFeature().contains(islem[1])) {
                                if (Friends.get(i).blockedFriends.contains(islem[1])) {
                                    Friends.get(i).blockedUsers.remove(islem[1]);
                                    System.out.println(islem[1] + " has been successfully unblocked");                  // unblock if user in list[1] is blocked
                                } else {
                                    System.out.println("No such user in your blocked-user list!");
                                }
                            } else {
                                System.out.println("No such user");
                            }

                        }
                    }
                } else {
                    System.out.println("Please sign in and try again.");
                }

            } else if (islem[0].equals("SHOWBLOCKEDFRIENDS")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);
                if (signin) {


                    for (int i = 0; i < Friends.size(); i++) {

                        if (Friends.get(i).userID == place.userID) {
                            if (Friends.get(i).blockedFriends.size() != 0){
                                for (int y = 0; y < Friends.get(i).blockedFriends.size(); y++) {
                                    int x = valuesoftxt.getFeature().indexOf(Friends.get(i).blockedFriends.get(y));
                                    if(x-1>=3)
                                    System.out.println("Name:" + valuesoftxt.getFeature().get(x - 1) + "\n" + "username:" + valuesoftxt.getFeature().get(x) + "\n" + "birthday:" + valuesoftxt.getFeature().get(x + 2) + "\n" + "school:" + valuesoftxt.getFeature().get(x + 3) + "\n" + "----------------------");
                                }
                        }else{
                                System.out.println("You haven’t blocked any friend yet!");
                            }
                        }
                    }
                } else {
                    System.out.println("Error: Please sign in and try again.");
                }


            } else if (islem[0].equals("SHOWBLOCKEDUSERS")) {
                System.out.println("------------------------" + "\n" + "commands:" + commandss);
                if (signin) {


                    for (int i = 0; i < Friends.size(); i++) {                                                             // We use it when printing by specifying the index from the list of user information.

                        if (Friends.get(i).userID == place.userID) {
                            if (Friends.get(i).blockedUsers.size() != 0){
                                for (int y = 0; y < Friends.get(i).blockedUsers.size(); y++) {
                                    int x = valuesoftxt.getFeature().indexOf(Friends.get(i).blockedUsers.get(y));
                                    if(x-1>=0)
                                    System.out.println("Name:" + valuesoftxt.getFeature().get(x - 1) + "\n" + "username:" + valuesoftxt.getFeature().get(x) + "\n" + "birthday:" + valuesoftxt.getFeature().get(x + 2) + "\n" + "school:" + valuesoftxt.getFeature().get(x + 3) + "\n" + "----------------------");
                                }
                        }else{
                                System.out.println("You haven’t blocked any user yet!");
                            }
                        }

                    }


                } else {
                    System.out.println("Error: Please sign in and try again.");
                }



            }
            else if(islem[0].equals("SIGNOUT")){
                signin=false;
                index=0;                                                                                                // By resetting the index, we are preparing the code for the next user who logs in.


                System.out.println("You have successfully signed out.");
            }

        }

    }                                                                                                                   // methods I use in mysocialbook class

    public static void createFriends(int userID, String name,String username,String password,Date birthDate,String highschool,ArrayList friendlisttt,ArrayList blockedFriends,ArrayList blockedUsers ){
        Friend info=new Friend(userID,name,username,password,birthDate,highschool,friendlisttt,blockedFriends,blockedUsers);
        Friends.add(info);

    }



    public static void createUser(int userID, String name, String username, String password, Date birthDate, String highschool){

        User personal=new User(userID,name,username,password,birthDate,highschool);
        Users.add(personal);
    }
    public static void createPost(int userID, String name, String username, String password, Date birthDate, String highschool, ArrayList friendList,ArrayList blockedFriends,ArrayList blockedUsers, ArrayList textOfPostLists, ArrayList imageOfPostLists, ArrayList videoOfPostLists,ArrayList allPosts){
        UsersPost information=new UsersPost(userID,name,username,password,birthDate,highschool,friendList,blockedFriends,blockedUsers,textOfPostLists,imageOfPostLists,videoOfPostLists,allPosts);
        UsersPosts.add(information);



    }




}
