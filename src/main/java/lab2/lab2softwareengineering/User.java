package lab2.lab2softwareengineering;

public class User
{
    private String username;
    private String password;
    private int failedLoginAttempts;
    private boolean blocked;
    //constructor
    public User(String username, String password)
    {
        checkUsername(username);
        checkPassword(password);
        this.username = username;
        this.password = password;
        this.failedLoginAttempts = 0;
        this.blocked = false;
    }
    //username validity check: limiting to under 50 characters and the specified structure
    private void checkUsername(String username)
    {
        if (username.length() > 50)
            throw new IllegalArgumentException("Username is too long, try something shorter");
        String emailRegex = "^[a-zA-Z0-9-+%_]+@[a-zA-Z0-9][a-zA-Z0-9.-]*\\.[a-zA-Z]{2,}$";
        if (!username.matches(emailRegex))
            throw new IllegalArgumentException("Please enter a valid Email as username");
    }
    //password validity check: limiting the length to 8-12 characters and forcing at least 1 letter, 1 digit and 1 special char
    private void checkPassword(String password)
    {
        //checking length
        if (password.length() < 8)
            throw new IllegalArgumentException("Your password is too short, add more characters");
        if (password.length() > 12)
            throw new IllegalArgumentException("Your password is too long, try a shorter one");
        String passwordRegex = "^[a-zA-Z0-9!@#$%^&*()]+$";

        if (!password.matches(passwordRegex))
            throw new IllegalArgumentException("Please enter a valid password");
        //checking if there is at least 1 letter, 1 digit and 1 special character
        boolean includesLetter = false;
        boolean includesNumber = false;
        boolean includesSign = false;

        for (int i = 0; i < password.length(); i++)
        {
            char c = password.charAt(i);
            if (Character.isLetter(c))
                includesLetter = true;
            else if (Character.isDigit(c))
                includesNumber = true;
            else if (c == '!' || c == '@' || c == '#' || c == '$' || c == '%' || c == '^' || c == '&' || c == '*' || c == '(' || c == ')')
                includesSign = true;
            else
                throw new IllegalArgumentException("Please enter a valid password");

        }
        if (!includesLetter || !includesNumber || !includesSign)
            throw new IllegalArgumentException("Please enter a valid password");

    }

    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }
    //method for checking if the user is blocked - thread safe
    public synchronized boolean isBlocked() { return blocked; }
    //method for blocking the user - thread safe
    public synchronized void block() { blocked = true; }
    //method for unblocking the user and resetting the attempts as well - thread safe
    public synchronized void unblock()
    {
        blocked = false;
        failedLoginAttempts = 0;
    }
    //getter for failed login attempts - thread safe
    public synchronized int getFailedLoginAttempts() { return failedLoginAttempts; }
    //method for updating the count of failed login attempts in a row - thread safe
    public synchronized void addFailedAttempt() { failedLoginAttempts++; }
    //method for resetting the number of failed login attempts - thread safe
    public synchronized void resetFailedLoginAttempts() { failedLoginAttempts = 0; }

    //toString override for printing the valid usernames with their passwords
    @Override
    public String toString()
    {
        return username + " " + password;
    }
}
