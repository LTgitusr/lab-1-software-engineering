package lab2.lab2softwareengineering;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginApplication extends Application
{
    private static int maxFailedLoginAttempts;
    private static int loginTimeout;

    @Override
    public void start(Stage stage) throws IOException
    {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        maxFailedLoginAttempts = Integer.parseInt(args[0]);
        loginTimeout = Integer.parseInt(args[1]);

        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Users Login");
        stage.setScene(scene);
        stage.show();
    }

    public static int getMaxFailedLoginAttempts()
    {
        return maxFailedLoginAttempts;
    }

    public static int getLoginTimeout()
    {
        return loginTimeout;
    }
}
