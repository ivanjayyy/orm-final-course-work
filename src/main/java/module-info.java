module com.ijse.gdse73.elitedrivingschoolmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires javafx.graphics;
    requires com.jfoenix;
    requires java.desktop;
    requires jbcrypt;
    requires javafx.base;

    opens com.ijse.gdse73.elitedrivingschoolmanagementsystem.config to jakarta.persistence;
    opens com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity to org.hibernate.orm.core;
    opens com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller to javafx.fxml;

    exports com.ijse.gdse73.elitedrivingschoolmanagementsystem;
    opens com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.course to javafx.fxml;
    opens com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.instructor to javafx.fxml;
    opens com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.student to javafx.fxml;
    opens com.ijse.gdse73.elitedrivingschoolmanagementsystem.controller.user to javafx.fxml;

    opens com.ijse.gdse73.elitedrivingschoolmanagementsystem.tm to javafx.base;
}