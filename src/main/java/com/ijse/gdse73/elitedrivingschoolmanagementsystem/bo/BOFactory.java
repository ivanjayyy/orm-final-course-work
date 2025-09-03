package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        return (boFactory == null) ? new BOFactory() : boFactory;
    }

    public SuperBO getBO(BOTypes boType) {
        return switch (boType) {
            case USER -> new UserBOImpl();
            case COURSE -> new CourseBOImpl();
            case INSTRUCTOR -> new InstructorBOImpl();
            case STUDENT -> new StudentBOImpl();
            case PAYMENT -> new PaymentBOImpl();
            case LESSON -> new LessonBOImpl();
        };
    }

}
