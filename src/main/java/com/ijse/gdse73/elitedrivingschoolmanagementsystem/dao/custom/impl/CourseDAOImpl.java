package com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.impl;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.config.FactoryConfiguration;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.CourseDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Course;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class CourseDAOImpl implements CourseDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Course course) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(course);
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
    public boolean update(Course course) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(course);
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
            Course course = session.get(Course.class, id);

            if (course != null) {
                session.remove(course);
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
    public ArrayList<Course> search(String id) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Course> courseList;

        try {
            Query query = session.createQuery("FROM Course WHERE id = :courseId", Course.class);
            query.setParameter("courseId", id);

            courseList = (ArrayList<Course>) query.getResultList();
            transaction.commit();
            return courseList;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<Course> getAll() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Course> courseList;

        try {
            Query query = session.createQuery("FROM Course", Course.class);

            courseList = (ArrayList<Course>) query.getResultList();
            transaction.commit();
            return courseList;

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
            Query query = session.createQuery("SELECT MAX(id) FROM Course", String.class);
            String maxId = String.valueOf(query);

            if (maxId != null) {
                String numericPart = maxId.replaceAll("[^0-9]", "");
                int number = Integer.parseInt(numericPart);
                number++;
                nextId = "C" + String.format("%04d", number);

            } else {
                nextId = "C1001";
            }
            transaction.commit();
            return nextId;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return "C1001";

        } finally {
            session.close();
        }
    }

}
