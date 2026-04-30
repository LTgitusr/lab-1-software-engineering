package lab2.lab2softwareengineering.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import lab2.lab2softwareengineering.UsersApp;
import lab2.lab2softwareengineering.Utilities.switchScenes;

import java.io.IOException;

public class LoginController {

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    private UsersApp usersApp;

    @FXML
    public void initialize()
    {
        usersApp = new UsersApp("users.txt");
        errorLabel.setText("");
    }

    @FXML
    void onLoginButtonClick(ActionEvent event)
    {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(usersApp.isValid(username, password))
        {
            try
            {
                switchScenes.switchScene(usernameField, "welcome.fxml");
            }
            catch(IOException e)
            {
                errorLabel.setText("Couldn't load the next screen");
            }
        }
        else
            errorLabel.setText("user or password do not match");
    }
}