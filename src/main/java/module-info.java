module com.ijse.gdse73.elitedrivingschoolmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires com.ijse.gdse73.elitedrivingschoolmanagementsystem;

    opens com.ijse.gdse73.elitedrivingschoolmanagementsystem.config to jakarta.persistence;
    opens com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity to org.hibernate.orm.core;
    opens com.ijse.gdse73.elitedrivingschoolmanagementsystem to javafx.fxml;

    exports com.ijse.gdse73.elitedrivingschoolmanagementsystem;
}