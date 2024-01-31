package com.mindera.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindera.users.controller.UserController;
import com.mindera.users.entity.User;
import com.mindera.users.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.invocation.mockref.MockReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UsersApplicationTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    private UsersRepository usersRepository;

    User user1 = new User(1L, "Bruna", "1111");
    User user2 = new User(2L, "Chico", "2222");
    User user3 = new User(3L, "Rodrigo", "3333");

    @Test
    void contextLoads() {
    }

    @BeforeEach
    public void setup() {
        User user1 = new User(1L, "Bruna", "1111");
        User user2 = new User(2L, "Chico", "2222");
        User user3 = new User(3L, "Rodrigo", "3333");
        List<User> userList = new ArrayList<>();
        Mockito.mock(UsersRepository.class);
    }

    @Test
    void getAllUserSuccess() throws Exception {
        List<User> usersTest = new ArrayList<>(Arrays.asList(user1, user2, user3));
        Mockito.when(usersRepository.findAll()).thenReturn(usersTest);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].username", is("Chico")))
                .andDo(e-> System.out.println(e.getResponse().getContentAsString()));
    }

    @Test
    void getAllUserNotSuccess() throws Exception {

    }

    @Test
    void addUserSuccess() throws Exception {
        User userAdd = User.builder()
                .id(3L)
                .username("Rodrigo")
                .password("3333")
                .build();
        Mockito.when(usersRepository.save(userAdd)).thenReturn(userAdd);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userAdd));
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.username", is("Rodrigo")))
                .andDo(e-> System.out.println(e.getResponse().getContentAsString()));

    }

    @Test
    void addUserNotSuccess() throws Exception {

    }

    @Test
    void getByIdUserSuccess() throws Exception {
        Mockito.when(usersRepository.findById(1L)).thenReturn(Optional.ofNullable((user1)));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(e -> System.out.println(e.getResponse().getContentAsString()))
                .andReturn();

    }

    @Test
    void getByIdUserNotSuccess() throws Exception {

    }

    @Test
    void updatePutUserSuccess() throws Exception {
        User userPutUpdated = User.builder()
                .id(2L)
                .username("Chico")
                .password("2222")
                .build();
        Mockito.when(usersRepository.getReferenceById(user2.getId())).thenReturn((user2));
        Mockito.when(usersRepository.save(userPutUpdated)).thenReturn(userPutUpdated);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put("/user/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userPutUpdated));
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.username", is("Chico")))
                .andDo(e -> System.out.println(e.getResponse().getContentAsString()));
    }

    @Test
    void updatePatchUserSuccess() throws Exception{
       User userPatchUpdated = User.builder()
               .id(3L)
               .username("Rodrigo")
               .password("3333")
               .build();
        Mockito.when(usersRepository.findById(user3.getId())).thenReturn(Optional.ofNullable(user3));
        Mockito.when(usersRepository.save(userPatchUpdated)).thenReturn(userPatchUpdated);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .patch("/user⁄{id}",3)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userPatchUpdated));
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.password", is("3333")))
                .andDo(e -> System.out.println(e.getResponse().getContentAsString()));
    }

    @Test
    void updateUserNotSuccess() throws Exception{
        User userNotId = User.builder()
                .username("Bruna")
                .password(null)
                .build();
        Mockito.when(usersRepository.getReferenceById(1L)).thenReturn((userNotId));
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put("/user/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userNotId));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isBadRequest())
                .andDo(e -> System.out.println(e.getResponse().getContentAsString()))
                .andReturn();
    }



}

