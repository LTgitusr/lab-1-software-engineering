package lab2.lab2softwareengineering.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import lab2.lab2softwareengineering.UsersApp;
import lab2.lab2softwareengineering.Utilities.SwitchScenes;
import lab2.lab2softwareengineering.LoginStatus;
import lab2.lab2softwareengineering.LoginApplication;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    private UsersApp usersApp;
    //gets the runtime parameters, creates the user list and clears the error message
    @FXML
    public void initialize()
    {
        usersApp = new UsersApp("users.txt", LoginApplication.getMaxFailedLoginAttempts(), LoginApplication.getLoginTimeout());
        errorLabel.setText("");
    }

    @FXML
    void onLoginButtonClick(ActionEvent event)
    {
        //gets the username and password entered
        String username = usernameField.getText();
        String password = passwordField.getText();
        //gets login status
        LoginStatus status = usersApp.login(username, password);
        //switches to welcome screen if the login info is valid and the user isn't blocked, otherwise - displays error message
        if(status == LoginStatus.VALID)
        {
            try
            {
                SwitchScenes.switchScene(usernameField, "welcome.fxml");
            }
            catch(IOException e)
            {
                errorLabel.setText("Failed to load the next screen");
            }
        }
        else if(status == LoginStatus.BLOCKED)
            errorLabel.setText("Too many failed login attempts. Please try again later.");
        else
            errorLabel.setText("user or password do not match");
    }
}