package com.ijse.gdse73.elitedrivingschoolmanagementsystem;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.config.FactoryConfiguration;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Test;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AppInitializer {
    public static void main(String[] args) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Test test = new Test("1","Ivan");
        session.persist(test);

        transaction.commit();
        session.close();
    }
}
