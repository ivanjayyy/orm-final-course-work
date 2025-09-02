package com.ijse.gdse73.elitedrivingschoolmanagementsystem.config;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.InputStream;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private final SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(Lesson.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Test.class);

        Properties properties = new java.util.Properties();
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
            if (input != null) {
                properties.load(input);
                configuration.addProperties(properties);
            } else {
                throw new java.io.FileNotFoundException("hibernate.properties not found in classpath");
            }
        } catch (java.io.IOException e) {
            throw new RuntimeException("Failed to load hibernate.properties", e);
        }

        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        return factoryConfiguration == null ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
