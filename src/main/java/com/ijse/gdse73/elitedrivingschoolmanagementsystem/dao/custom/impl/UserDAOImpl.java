package com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.impl;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.config.FactoryConfiguration;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.UserDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.User;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(User user) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(user);
            transaction.commit();
            return true;

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;

        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(User user) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(user);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;

        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String id) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            User user = session.get(User.class, id);

            if (user != null) {
                session.remove(user);
                transaction.commit();
                return true;
            }
            return false;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;

        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<User> search(String id) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<User> userList;

        try {
            Query query = session.createQuery("FROM User WHERE id = :userId", User.class);
            query.setParameter("userId", id);

            userList = (ArrayList<User>) query.getResultList();
            transaction.commit();
            return userList;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<User> getAll() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<User> userList;

        try {
            Query query = session.createQuery("FROM User", User.class);

            userList = (ArrayList<User>) query.getResultList();
            transaction.commit();
            return userList;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }
    }

    @Override
    public String getNextId() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        String nextId;

        try {
            Query query = session.createQuery("SELECT MAX(id) FROM User", String.class);
            String maxId = String.valueOf(query);

            if (maxId != null) {
                String numericPart = maxId.replaceAll("[^0-9]", "");
                int number = Integer.parseInt(numericPart);
                number++;
                nextId = "U" + String.format("%04d", number);

            } else {
                nextId = "U1001";
            }
            transaction.commit();
            return nextId;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return "U1001";

        } finally {
            session.close();
        }
    }

}
