package com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.impl;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.config.FactoryConfiguration;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.LessonDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Lesson;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class LessonDAOImpl implements LessonDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Lesson lesson) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(lesson);
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
    public boolean update(Lesson lesson) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(lesson);
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
            Lesson lesson = session.get(Lesson.class, id);

            if (lesson != null) {
                session.remove(lesson);
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
    public ArrayList<Lesson> search(String id) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Lesson> lessonList;

        try {
            Query query = session.createQuery("FROM Lesson WHERE id = :lessonId", Lesson.class);
            query.setParameter("lessonId", id);

            lessonList = (ArrayList<Lesson>) query.getResultList();
            transaction.commit();
            return lessonList;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<Lesson> getAll() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Lesson> lessonList;

        try {
            Query query = session.createQuery("FROM Lesson", Lesson.class);

            lessonList = (ArrayList<Lesson>) query.getResultList();
            transaction.commit();
            return lessonList;

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
            Query query = session.createQuery("SELECT MAX(id) FROM Lesson ", String.class);
            String maxId = String.valueOf(query);

            if (maxId != null) {
                String numericPart = maxId.replaceAll("[^0-9]", "");
                int number = Integer.parseInt(numericPart);
                number++;
                nextId = "L" + String.format("%04d", number);

            } else {
                nextId = "L1001";
            }
            transaction.commit();
            return nextId;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return "L1001";

        } finally {
            session.close();
        }
    }

}
