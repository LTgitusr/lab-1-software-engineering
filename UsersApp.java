import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class UsersApp
{
    public static void main(String[] args) throws FileNotFoundException
    {
        ArrayList<User> users = new ArrayList<>();
        File file = new File("users.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine())
        {
            //tokenizing each line into a username and a password
            String line = scanner.nextLine().trim();
            String[] parts = line.split("\\s+");
            String username = parts[0];
            String password = parts[1];
            //making sure the users are valid else there's an exception thrown
            try
            {
                User user = new User(username, password);
                users.add(user);
            }
            catch (IllegalArgumentException e)
            {
                System.out.println(line + " --> " + e.getMessage());
            }
        }
        scanner.close();
        //sorting all usernames alphabetically
        Collections.sort(users, (u1, u2) -> u1.getUsername().compareToIgnoreCase(u2.getUsername()));
        //Write Users to File
        try
        {
            FileWriter fileOutput = new FileWriter("out.txt");
            //concatenating all valid users + their passwords (sorted)
            for(User user : users)
            {
                fileOutput.append(user.toString()).append("\n");
            }

            fileOutput.flush();
            fileOutput.close();
        }
        catch (IOException e)
        {
            System.out.println("Writing to out.txt failed!");
        }
    }
}
