package lab2.lab2softwareengineering.Utilities;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SwitchScenes
{
    //the method replaces the current scene with the specified one
    public static void switchScene(Node node, String fxmlFile) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(SwitchScenes.class.getResource("/lab2/lab2softwareengineering/" + fxmlFile));

        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
