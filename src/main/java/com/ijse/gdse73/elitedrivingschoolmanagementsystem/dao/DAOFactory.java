package com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? new DAOFactory() : daoFactory;
    }

    public SuperDAO getDAO(DAOTypes daoType) {
        return switch (daoType) {
            case User -> new UserDAOImpl();
            case Course -> new CourseDAOImpl();
            case Instructor -> new InstructorDAOImpl();
            case Student -> new StudentDAOImpl();
            case Payment -> new PaymentDAOImpl();
            case Lesson -> new LessonDAOImpl();
        };
    }

}
