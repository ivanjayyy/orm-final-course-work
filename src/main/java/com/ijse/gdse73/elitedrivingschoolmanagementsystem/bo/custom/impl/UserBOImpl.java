package com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.impl;

import com.ijse.gdse73.elitedrivingschoolmanagementsystem.bo.custom.UserBO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.DAOFactory;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.DAOTypes;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dao.custom.UserDAO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.dto.UserDTO;
import com.ijse.gdse73.elitedrivingschoolmanagementsystem.entity.User;

import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOTypes.USER);

    @Override
    public boolean saveUser(UserDTO userDTO) {
        return userDAO.save(new User(userDTO.getUserId(),userDTO.getName(),userDTO.getUsername(),userDTO.getPassword(),userDTO.getEmail(),userDTO.isAdmin()));
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        return userDAO.update(new User(userDTO.getUserId(),userDTO.getName(),userDTO.getUsername(),userDTO.getPassword(),userDTO.getEmail(),userDTO.isAdmin()));
    }

    @Override
    public boolean deleteUser(String id) {
        return userDAO.delete(id);
    }

    @Override
    public ArrayList<UserDTO> searchUser(String id) {
        ArrayList<User> users = userDAO.search(id);
        ArrayList<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            userDTOS.add(new UserDTO(user.getId(),user.getName(),user.getUsername(),user.getPassword(),user.getEmail(),user.isAdmin()));
        }
        return userDTOS;
    }

    @Override
    public ArrayList<UserDTO> getAllUsers() {
        ArrayList<User> users = userDAO.getAll();
        ArrayList<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            userDTOS.add(new UserDTO(user.getId(),user.getName(),user.getUsername(),user.getPassword(),user.getEmail(),user.isAdmin()));
        }
        return userDTOS;
    }

    @Override
    public String getNextUserId() {
        return userDAO.getNextId();
    }
}
