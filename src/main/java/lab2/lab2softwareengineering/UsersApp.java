package lab2.lab2softwareengineering;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UsersApp
{
    private ArrayList<User> users;

    private int maxFailedLogins;
    private int loginTimeout;
    //constructor
    public UsersApp(String filename, int maxFailedLogins, int loginTimeout)
    {
        users = new ArrayList<>();
        readUsers(filename);
        this.maxFailedLogins = maxFailedLogins;
        this.loginTimeout = loginTimeout;
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
    //method for finding a user in the saved list of users
    private User findUser(String username)
    {
        for (User user : users)
        {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }
    //method for updating failed login attempts and indicating if the user is supposed to get blocked
    private boolean failedAttemptsUpdateThreaded(User user)
    {
        //initializing a reference to a value(1 slot array) to allow changing the returned result
        final boolean[] gotBlocked = new boolean[1];
        Thread failedAttempt = new Thread(() ->
        {
           user.addFailedAttempt();
           //blocks the user for the specified amount of seconds if the failed attempts reached the max
           if(user.getFailedLoginAttempts() >= maxFailedLogins)
           {
               user.block();
               gotBlocked[0] = true;
               Thread timeout = new Thread(() ->
               {
                   try
                   {
                       Thread.sleep(1000L * loginTimeout);
                   }
                   catch(InterruptedException e)
                   {
                       System.out.println("Timeout was interrupted");
                   }
                   user.unblock();
               });
               timeout.start();
           }
        });
        failedAttempt.start();

        try
        {
            failedAttempt.join();
        }
        catch(InterruptedException e)
        {
            System.out.println("Failed attempt was interrupted");
        }
        return gotBlocked[0];
    }
    //method for checking if the user is currently blocked
    private boolean blockCheckThreaded(User user)
    {
        //initializing a reference to a value(1 slot array) to allow changing the returned result
        final boolean[] blocked = new boolean[1];
        Thread blockCheck = new Thread(() ->
        {
            blocked[0] = user.isBlocked();
        });
        blockCheck.start();
        try
        {
            blockCheck.join();
        }
        catch(InterruptedException e)
        {
            System.out.println("Block check was interrupted");
        }
        return blocked[0];
    }
    //method for logging in that returns a status of the login attempts after receiving the username and password entered
    public LoginStatus login(String username, String password)
    {
        User user = findUser(username);
        //no user found means info is invalid
        if(user == null)
            return LoginStatus.INVALID;
        //if password doesn't match the login is invalid and if the user also reached the attempt limit - login is blocked
        if(!user.getPassword().equals(password))
        {
            if(failedAttemptsUpdateThreaded(user))
                return LoginStatus.BLOCKED;
            return LoginStatus.INVALID;
        }
        //returns blocked status if the user is already in login timeout
        if(blockCheckThreaded(user))
        {
            return LoginStatus.BLOCKED;
        }
        //if the password matches the username the attempt is valid and if so, we reset the number of failed login attempts
        user.resetFailedLoginAttempts();
        return LoginStatus.VALID;
    }
}