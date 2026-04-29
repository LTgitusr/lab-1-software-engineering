module lab2.lab2softwareengineering {
    requires javafx.controls;
    requires javafx.fxml;


    opens lab2.lab2softwareengineering to javafx.fxml;
    exports lab2.lab2softwareengineering;
    exports lab2.lab2softwareengineering.Controllers;
    opens lab2.lab2softwareengineering.Controllers to javafx.fxml;
}