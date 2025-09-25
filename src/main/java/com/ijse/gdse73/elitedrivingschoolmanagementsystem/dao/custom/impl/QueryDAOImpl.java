package com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.impl;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.config.FactoryConfiguration;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.QueryDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public List<Student> getStudentsRegisteredForAllCourses() {
        Session session = factoryConfiguration.getSession();
        Query<Student> query = session.createQuery("SELECT s FROM Student s CROSS JOIN Course c LEFT JOIN s.course sc WHERE sc IS NOT NULL GROUP BY s.id, s.name HAVING COUNT(c) = (SELECT COUNT(c2) FROM Course c2)", Student.class);
        return query.list();
    }
}
