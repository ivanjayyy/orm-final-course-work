package com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao;

import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {

    boolean save(T xxx);
    boolean update(T xxx);
    boolean delete(String xxx);
    ArrayList<T> search(String xxx);
    ArrayList<T> getAll();
    String getNextId();

}
