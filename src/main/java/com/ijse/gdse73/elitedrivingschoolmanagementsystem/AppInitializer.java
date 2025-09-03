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

        Course course = new Course();
        course.setId("C001");
        course.setName("Java");
        course.setDescription("Java Programming");
        course.setDuration(10);
        course.setFee(BigDecimal.valueOf(100));

        Course course2 = new Course();
        course2.setId("C002");
        course2.setName("Python");
        course2.setDescription("Python Programming");
        course2.setDuration(10);
        course2.setFee(BigDecimal.valueOf(200));

        CourseDAOImpl courseDAO = new CourseDAOImpl();
        boolean isSaved = courseDAO.save(course);
        System.out.println(isSaved);

        boolean isSaved2 = courseDAO.save(course2);
        System.out.println(isSaved2);

        Course course3 = new Course();
        course3.setId("C002");
        course3.setName("Ivan");
        course3.setDescription("Python Programming");
        course3.setDuration(10);
        course3.setFee(BigDecimal.valueOf(200));

        boolean isUpdated = courseDAO.update(course3);
        System.out.println("///////////////////////");
        System.out.println(isUpdated);

        ArrayList<Course> courseList = courseDAO.getAll();
        for (Course c : courseList) {
            System.out.println("////");
            System.out.println(c.getId() + " " + c.getName() + " " + c.getDescription() + " " + c.getDuration() + " " + c.getFee());
        }
    }
}
