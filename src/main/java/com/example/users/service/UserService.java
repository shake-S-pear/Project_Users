package com.example.users.service;

import com.example.users.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserService {

    User addUser(User user);
    List<User> getAllUsers();
    User getUserById(int id);
    User deleteUserById(int id);
    User saveOrUpdateUser(User newUser, int id);
    User patch(Map<String, String> update, int id);
    List<User> findByAgeBetween(Date startAge, Date endAge);

}
