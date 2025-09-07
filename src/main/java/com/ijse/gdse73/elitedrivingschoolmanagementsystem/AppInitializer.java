package com.ijse.gdse73.elitedrivingschoolmanagementsystem;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.UserBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.UserDTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

public class AppInitializer extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        String password = BCrypt.hashpw("813311", BCrypt.gensalt(12));

        UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);
        UserDTO userDTO = new UserDTO("U1001","Ivan Adithya","ivan123",password,"ivanjayasooriya03@gmail.com",true);
        userBO.saveUser(userDTO);



        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.setResizable(false);
        stage.show();
    }
}
