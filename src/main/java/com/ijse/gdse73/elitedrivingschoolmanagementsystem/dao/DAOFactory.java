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
            case USER -> new UserDAOImpl();
            case COURSE -> new CourseDAOImpl();
            case INSTRUCTOR -> new InstructorDAOImpl();
            case STUDENT -> new StudentDAOImpl();
            case PAYMENT -> new PaymentDAOImpl();
            case LESSON -> new LessonDAOImpl();
        };
    }

}
