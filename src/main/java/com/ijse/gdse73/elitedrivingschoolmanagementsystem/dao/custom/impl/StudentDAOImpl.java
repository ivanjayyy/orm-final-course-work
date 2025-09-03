package com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.impl;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.config.FactoryConfiguration;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.StudentDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Student;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class StudentDAOImpl implements StudentDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public boolean save(Student student) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(student);
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
    public boolean update(Student student) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.merge(student);
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
            Student student = session.get(Student.class, id);

            if (student != null) {
                session.remove(student);
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
    public ArrayList<Student> search(String id) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Student> studentList;

        try {
            Query query = session.createQuery("FROM Student WHERE id = :studentId", Student.class);
            query.setParameter("studentId", id);

            studentList = (ArrayList<Student>) query.getResultList();
            transaction.commit();
            return studentList;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;

        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<Student> getAll() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Student> studentList;

        try {
            Query query = session.createQuery("FROM Student", Student.class);

            studentList = (ArrayList<Student>) query.getResultList();
            transaction.commit();
            return studentList;

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
            Query query = session.createQuery("SELECT MAX(id) FROM Student", String.class);
            String maxId = String.valueOf(query);

            if (maxId != null) {
                String numericPart = maxId.replaceAll("[^0-9]", "");
                int number = Integer.parseInt(numericPart);
                number++;
                nextId = "S" + String.format("%04d", number);

            } else {
                nextId = "S1001";
            }
            transaction.commit();
            return nextId;

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return "S1001";

        } finally {
            session.close();
        }
    }

}
