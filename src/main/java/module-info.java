module com.ijse.gdse73.elitedrivingschoolmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ijse.gdse73.elitedrivingschoolmanagementsystem to javafx.fxml;
    exports com.ijse.gdse73.elitedrivingschoolmanagementsystem;
}