public class User
{
    private String username;
    private String password;

    public User(String username, String password)
    {
        checkUsername(username);
        checkPassword(password);
        this.username = username;
        this.password = password;
    }

    private void checkUsername(String username)
    {
        if (username.length() > 50)
            throw new IllegalArgumentException("Username is too long, try something shorter");
        String emailRegex = "^[a-zA-Z0-9-+%_]+@[a-zA-Z0-9][a-zA-Z0-9.-]*\\.[a-zA-Z]{2,}$";
        if (!username.matches(emailRegex))
            throw new IllegalArgumentException("Please enter a valid Email as username");
    }

    private void checkPassword(String password)
    {
        if (password.length() < 8)
            throw new IllegalArgumentException("Your password is too short, add more characters");
        if (password.length() > 12)
            throw new IllegalArgumentException("Your password is too long, try a shorter one");
        String passwordRegex = "^[a-zA-Z0-9!@#$%^&*()\\-+_=]+$";

        if (!password.matches(passwordRegex))
            throw new IllegalArgumentException("Please enter a valid password");

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
            else if (c == '!' || c == '@' || c == '#' || c == '$' || c == '%' || c == '^' || c == '&' || c == '*' || c == '(' || c == ')' || c == '_' || c == '+' || c == '-' || c == '=')
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

    @Override
    public String toString()
    {
        return username + " " + password;
    }
}
