package com.example.users.controller;

import com.example.users.entity.User;
import com.example.users.exception.UserNotFoundException;
import com.example.users.exception.UserUnSupportedFieldPatchException;
import com.example.users.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;
    private User user1, user2, user3;
    private List<User> userList;
    @Value(value = "${data.value.limitOfUserAge}")
    private int limitOfAge;


    @BeforeEach
    public void setup() throws ParseException {
        user1 = new User(101, "John", "Jackson", new SimpleDateFormat("dd-MM-yyyy").parse("26-09-1989"), "john.jackson@yahoo.com");
        user2 = new User(201, "Olha", "English", new SimpleDateFormat("dd-MM-yyyy").parse("10-01-2001"), "olha.english@ukr.net");
        user3 = new User(301, "Olena", "Shevchenko", new SimpleDateFormat("dd-MM-yyyy").parse("05-12-1933"), "olena.shevchenko@google.com");
        userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
    }

    @AfterEach
    void tearDown() {
        user1 = user2 = user3 = null;
        userList.clear();
    }


    @Test
    @DisplayName("test: get/allUsers")
    public void getAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].firstname", is("Olena")))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].id", hasItem(101)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(301, 101, 201)))
                .andExpect(jsonPath("$[*].lastname", hasItem("Shevchenko")))
                .andExpect(jsonPath("$[*].email", hasItem("olha.english@ukr.net")));

        assertThat(userList.size(), is(3));

        assertThat(userList, hasItem(user1));
        assertThat(userList, hasItem(user2));
        assertThat(userList, hasItem(user3));

        assertThat(userList, containsInAnyOrder(user3, user1, user2));
    }

    @Test
    @DisplayName("test: post/addUser")
    public void addUser() throws Exception{
        when(userService.addUser(user2)).thenReturn(user2);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/users/add")
                        .content(asJsonString(user2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("test: get/userById")
    public void getUserById_OK() throws Exception {
        when(userService.getUserById(101)).thenReturn(user1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/get/{id}", 101)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(101)))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstname", is("John")))
                .andExpect(jsonPath("$.lastname", is("Jackson")))
                .andExpect(jsonPath("$.lastname").isString())
                .andExpect(jsonPath("$.birthdate", is("1989-09-25T21:00:00.000+00:00")))
                .andExpect(jsonPath("$.email", is("john.jackson@yahoo.com")));
    }

    @Test
    @DisplayName("test: delete/userById")
    public void deleteUserById() throws Exception {
        when(userService.deleteUserById(user3.getId())).thenReturn(user3);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/delete/{id}", 301)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user3)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(301)));
    }

    @Test
    @DisplayName("test: put/updateUserById")
    public void saveOrUpdateUserById() throws Exception {

        User userPut = new User(201, "Olha", "English", new SimpleDateFormat("dd-MM-yyyy").parse("10-01-2001"), "olha.english@ukr.net", "050-505-50-05");
        when(userService.saveOrUpdateUser(userPut, 201)).thenReturn(userPut);

        mockMvc.perform( MockMvcRequestBuilders
                        .put("/users/update/{id}", 201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userPut)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test: get/usersBetweenTwoData")
    public void getData_between() throws Exception {

        String dateFrom = "12-03-1980";
        String dateTo = "12-03-2020";

        Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateFrom);
        Date toDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateTo);
        userList.remove(user3);

        when(userService.findByAgeBetween(fromDate, toDate)).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/fetch/{from_date}/{to_date}", dateFrom, dateTo)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].firstname", hasItem("John")))
                .andExpect(jsonPath("$[*].id").isNotEmpty())
                .andExpect(jsonPath("$[*].id", hasItem(101)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(101, 201)))
                .andExpect(jsonPath("$[*].lastname", hasItem("English")))
                .andExpect(jsonPath("$[*].email", hasItem("olha.english@ukr.net")));

        assertThat(userList.size(), is(2));

        assertThat(userList, hasItem(user1));
        assertThat(userList, hasItem(user2));

        assertThat(userList, containsInAnyOrder(user1, user2));
    }

    @Test
    @DisplayName("test: patch/updateUserById")
    public void patchUpdateUserById() throws Exception {

        String email = "olha.english.way2@ukr.net";
        User userPatch = new User(201, "Olha", "English", new SimpleDateFormat("dd-MM-yyyy").parse("10-01-2001"), email);
        Map<String, String> mapPatch = new HashMap<>();
        mapPatch.put("email", email);

        when(userService.patch(mapPatch, userPatch.getId())).thenReturn(userPatch);

        mockMvc.perform( MockMvcRequestBuilders
                        .patch("/users/update/{id}", 201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userPatch)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test: get/userById_Error")
    public void getUserById_Error() throws Exception {
        int idError = 701;
        when(userService.getUserById(idError)).thenThrow(new UserNotFoundException(idError));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/get/{id}", idError)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("test: delete/userById_Error")
    public void deleteUserById_Error() throws Exception {

        int idDelete = 801;
        User userDelete = new User(idDelete, "Olena", "Shevchenko", new SimpleDateFormat("dd-MM-yyyy").parse("05-12-1933"), "olena.shevchenko@google.com");

        when(userService.deleteUserById(idDelete)).thenThrow(new UserNotFoundException(idDelete));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/delete/{id}", idDelete)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDelete)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("test: patch/updateUserById_Error")
    public void patchUpdateUserById_Error() throws Exception {

        String email = "olha*english//way3";
        User userPatch = new User(201, "Olha", "English", new SimpleDateFormat("dd-MM-yyyy").parse("10-01-2001"), email);
        Map<String, String> mapPatch = new HashMap<>();
        mapPatch.put("email", email);

        when(userService.patch(mapPatch, userPatch.getId())).thenThrow(new UserUnSupportedFieldPatchException(mapPatch.keySet()));

        mockMvc.perform( MockMvcRequestBuilders
                        .patch("/users/update/{id}", mapPatch, 201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userPatch)))
                .andExpect(status().is4xxClientError());
    }

    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
