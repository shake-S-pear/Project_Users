package com.example.users.repository;

import com.example.users.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User user;


    @BeforeEach
    public void setUp() throws ParseException {
        user = new User(101, "John", "Jackson", new SimpleDateFormat("dd-MM-yyyy").parse("26-09-1989"), "john.jackson@yahoo.com");
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
        user = null;
    }

    @Test
    public void givenUserToAddShouldReturnAddedUser(){
        userRepository.save(user);
        User fetchedUser = userRepository.findById(user.getId()).get();
        assertEquals("John", fetchedUser.getFirstname());
    }

    @Test
    public void givenGetAllUsersShouldReturnListOfAllUsers() throws ParseException {
        User user1 = new User(101, "John", "Jackson", new SimpleDateFormat("dd-MM-yyyy").parse("26-09-1989"), "john.jackson@yahoo.com");
        User user2 = new User(201, "Olha", "English", new SimpleDateFormat("dd-MM-yyyy").parse("10-01-2001"), "olha.english@ukr.net");
        userRepository.save(user1);
        userRepository.save(user2);

        List<User> userList = new ArrayList<>();
        userList.add(userRepository.findById(user1.getId()).get());
        userList.add(userRepository.findById(user2.getId()).get());
        
        assertEquals("olha.english@ukr.net", userList.get(1).getEmail());
    }

    @Test
    public void givenIdThenShouldReturnUserOfThatId() throws ParseException {
        User user1 = new User(101, "John", "Jackson", new SimpleDateFormat("dd-MM-yyyy").parse("26-09-1989"), "john.jackson@yahoo.com");
        User user2 = userRepository.save(user1);

        Optional<User> optional =  userRepository.findById(user2.getId());
        assertEquals(user2.getId(), optional.get().getId());
        assertEquals(user2.getFirstname(), optional.get().getFirstname());
    }

    @Test
    public void givenIdToDeleteThenShouldDeleteTheUser() throws ParseException {
        User user1 = new User(101, "John", "Jackson", new SimpleDateFormat("dd-MM-yyyy").parse("26-09-1989"), "john.jackson@yahoo.com");
        userRepository.save(user1);
        userRepository.deleteById(user1.getId());
        Optional optional = userRepository.findById(user1.getId());
        assertEquals(Optional.empty(), optional);
    }

}
