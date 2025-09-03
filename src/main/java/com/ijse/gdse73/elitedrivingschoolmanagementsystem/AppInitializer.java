package com.ijse.gdse73.elitedrivingschoolmanagementsystem;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.config.FactoryConfiguration;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.impl.CourseDAOImpl;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Course;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Instructor;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Test;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AppInitializer {
    public static void main(String[] args) {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//
//        Test test = new Test("1","Ivan");
//        session.persist(test);
//
//        transaction.commit();
//        session.close();

//        Course course = new Course();
//        course.setId("C001");
//        course.setName("Java");
//        course.setDescription("Java Programming");
//        course.setDuration(10);
//        course.setFee(BigDecimal.valueOf(100));
//
//        CourseDAOImpl courseDAO = new CourseDAOImpl();
//        boolean isSaved = courseDAO.save(course);
//        System.out.println(isSaved);
//
//        ArrayList<Course> courseList = courseDAO.getAll();
//        for (Course c : courseList) {
//            System.out.println("////");
//            System.out.println(c.getId() + " " + c.getName() + " " + c.getDescription() + " " + c.getDuration() + " " + c.getFee());
//        }
    }
}
