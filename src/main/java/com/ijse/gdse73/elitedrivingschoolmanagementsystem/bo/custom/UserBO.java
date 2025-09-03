package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.SuperBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.UserDTO;

import java.util.ArrayList;

public interface UserBO extends SuperBO {

    boolean saveUser(UserDTO userDTO);
    boolean updateUser(UserDTO userDTO);
    boolean deleteUser(String id);
    ArrayList<UserDTO> searchUser(String id);
    ArrayList<UserDTO> getAllUsers();
    String getNextUserId();

}
