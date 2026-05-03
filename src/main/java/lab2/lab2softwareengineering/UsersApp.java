package lab2.lab2softwareengineering;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UsersApp
{
    private ArrayList<User> users;

    public UsersApp(String filename)
    {
        users = new ArrayList<>();
        readUsers(filename);
    }
    //method for reading users from a file and saving them in the class's user list
    private void readUsers(String filename)
    {
        try
        {
            File file = new File(filename);
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
        }
        catch (FileNotFoundException e)
        {
            System.out.println("file not found");
        }
    }
    //boolean method for checking if the username and the password entered are matching
    public boolean isValid(String username, String password)
    {
        for (User user : users)
        {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return true;
        }
        return false;
    }
}
