package com.ijse.gdse73.elitedrivingschoolmanagementsystem;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.BOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.CourseBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.InstructorBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.StudentBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.UserBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.CourseDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.InstructorDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.StudentDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.UserDTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AppInitializer extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        String password = BCrypt.hashpw("a", BCrypt.gensalt(12));

        UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);
        UserDTO userDTO = new UserDTO("U1001","Ivan Adithya","a",password,"ivanjayasooriya03@gmail.com",true);
        userBO.saveUser(userDTO);

        CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOTypes.COURSE);

        BigDecimal fee1 = new BigDecimal(10000);
        CourseDTO course1 = new CourseDTO("C1001","Science","Nothing Special",4,fee1);
        courseBO.saveCourse(course1);

        BigDecimal fee2 = new BigDecimal(50000);
        CourseDTO course2 = new CourseDTO("C1002","History","Nothing Special 2",6,fee2);
        courseBO.saveCourse(course2);

        BigDecimal fee3 = new BigDecimal(200000);
        CourseDTO course3 = new CourseDTO("C1003","Maths","Nothing Special 3",12,fee3);
        courseBO.saveCourse(course3);

        StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOTypes.STUDENT);
        ArrayList<String> courseIds = new ArrayList<>();
        courseIds.add("C1001");
        courseIds.add("C1002");
        courseIds.add("C1003");
        StudentDTO studentDTO = new StudentDTO("S1001","Male","Sadeepa Lakshan","Panadura","200304012446","0712349970","sadeepa2003@gmail.com",true,"2025-09-10",courseIds);
        studentBO.saveStudent(studentDTO);

        InstructorBO instructorBO = (InstructorBO) BOFactory.getInstance().getBO(BOTypes.INSTRUCTOR);
        InstructorDTO instructorDTO = new InstructorDTO("I1001","Pasindu Malmith","0712349970","pasii1999@gmail.com","C1001");
        instructorBO.saveInstructor(instructorDTO);








        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login Page");
        stage.setResizable(false);
        stage.show();
    }
}
