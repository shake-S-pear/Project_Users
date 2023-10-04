package com.example.users.controller;

import com.example.users.entity.User;
import com.example.users.service.UserService;
import com.example.users.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("add")
    public ResponseEntity<User> addUser(@RequestBody User user){

        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    @GetMapping("all")
    public ResponseEntity<List<User>> getAllUsers(){

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id){

        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
        ResponseEntity<User> responseEntity;
        User deletedUser = userService.deleteUserById(id);
        responseEntity = new ResponseEntity<>(deletedUser, HttpStatus.OK);

        return responseEntity;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> saveOrUpdate(@RequestBody User newUser, @PathVariable int id) {
        ResponseEntity<User> responseEntity;
        User updateUser = userService.saveOrUpdateUser(newUser, id);
        responseEntity = new ResponseEntity<>(updateUser, HttpStatus.OK);

        return responseEntity;
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<User> patch(@RequestBody Map<String, String> update, @PathVariable int id) {
        ResponseEntity<User> responseEntity;
        User patchUpdateUser = userService.patch(update, id);

        responseEntity = new ResponseEntity<>(patchUpdateUser, HttpStatus.OK);

        return responseEntity;
    }

    @GetMapping("/fetch/{from_date}/{to_date}")
    public ResponseEntity<List<User>> getData_between(@PathVariable(value = "from_date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date fromDate, @PathVariable(value = "to_date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date toDate) {

        return new ResponseEntity<>(userService.findByAgeBetween(fromDate, toDate), HttpStatus.OK);
    }

}
