import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class UserReading {
    public static int lengthofarray;
    public static ArrayList<String> array;

    public UserReading() throws IOException {
        array = new ArrayList<>();

        String line;
        String[] lineparts;
        Scanner pw = new Scanner(new BufferedReader(new FileReader("users.txt")));
        int userfeature =6 ;
        lengthofarray = 0;
        while (pw.hasNext()) {
            lengthofarray++;
            line = pw.nextLine();
            lineparts = line.split("\t");

            for (int i = 0; i < userfeature - 1; i++) {
                array.add(lineparts[i]);
            }

        }

    }

        public int getLengthofarray(){

        return lengthofarray;
        }

        public ArrayList<String> getFeature(){

        return array;
        }
    }









