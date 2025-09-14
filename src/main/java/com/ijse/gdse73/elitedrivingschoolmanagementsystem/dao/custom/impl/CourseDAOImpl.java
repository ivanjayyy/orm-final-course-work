package com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.impl;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.config.FactoryConfiguration;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.CourseDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Course;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
    public ArrayList<Course> search(String text) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Course> courseList;
        String searchText = "%" + text + "%";

        try {
            Query query = session.createQuery("FROM Course WHERE id LIKE :courseId OR name LIKE :courseName", Course.class);
            query.setParameter("courseId", searchText);
            query.setParameter("courseName", searchText);

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
            Query query = session.createQuery("SELECT id FROM Course ORDER BY id DESC", String.class);
            query.setMaxResults(1);
            String lastId = query.uniqueResult().toString();

            if (lastId != null) {
                String lastIdNumberString = lastId.substring(1);
                int lastIdNumber = Integer.parseInt(lastIdNumberString);
                int nextIdNumber = lastIdNumber + 1;
                return String.format("C%03d", nextIdNumber);

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
