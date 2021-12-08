package com.leverx.project.rest;

import com.leverx.project.application.Application;
import com.leverx.project.model.User;
import com.leverx.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Slf4j
public class AuthenticationRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldRegister() throws Exception {
        log.info("Execute test AuthenticationRestControllerTest shouldRegister()");
        when(userService.create(any(User.class), any(Integer.TYPE))).thenReturn(new User());
        mvc.perform(post("/api/auth/register/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("""
                        {
                            "first_name": "TestFirstName",
                            "last_name": "TestLastName",
                            "email": "test@gmail.com",
                            "password": "test"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("TestFirstName"))
                .andExpect(jsonPath("$.last_name").value("TestLastName"))
                .andExpect(jsonPath("$.email").value("test@gmail.com"));
    }
}
