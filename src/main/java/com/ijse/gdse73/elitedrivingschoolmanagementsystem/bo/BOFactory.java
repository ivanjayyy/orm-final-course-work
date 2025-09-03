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
            case User -> new UserBOImpl();
            case Course -> new CourseBOImpl();
            case Instructor -> new InstructorBOImpl();
            case Student -> new StudentBOImpl();
            case Payment -> new PaymentBOImpl();
            case Lesson -> new LessonBOImpl();
        };
    }

}
