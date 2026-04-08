import java.io.File;
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
            String line = scanner.nextLine().trim();
            String[] parts = line.split("\\s+");
            String username = parts[0];
            String password = parts[1];

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
        Collections.sort(users, (u1, u2) -> u1.getUsername().compareToIgnoreCase(u2.getUsername()));

        for(User user : users)
        {
            System.out.println(user);
        }
    }
}
