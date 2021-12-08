package com.leverx.project.rest;

import com.leverx.project.application.Application;
import com.leverx.project.model.Game;
import com.leverx.project.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN"})
@Slf4j
public class AdminGameRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setup() { mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); }

    @Test
    void shouldCreateGame() throws Exception {
        log.info("Execute test AdminGameRestControllerTest shouldCreateGame()");
        when(gameService.create(any(Game.class))).thenReturn(new Game(1, "Dota"));
        mvc.perform(post("/api/admin/games/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("""
                        {
                            "name": "Dota"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dota"));
    }

    @Test
    void shouldUpdateGame() throws Exception {
        log.info("Execute test AdminGameRestControllerTest shouldUpdateGame()");
        when(gameService.update(any(Integer.TYPE), any(Game.class))).thenReturn(new Game(1, "CS:GO"));
        mvc.perform(patch("/api/admin/games/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("""
                        {
                            "name": "CS:GO"
                        }
                        """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("CS:GO"));
    }

    @Test
    void shouldFindGameById() throws Exception {
        log.info("Execute test AdminGameRestControllerTest shouldFindGameById()");
        when(gameService.getById(any(Integer.TYPE))).thenReturn(Optional.of(new Game(1, "Dota")));
        mvc.perform(get("/api/admin/games/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Dota"));
    }

    @Test
    void shouldFindAll() throws Exception {
        log.info("Execute test AdminGameRestControllerTest shouldFindAll()");
        when(gameService.getAll()).thenReturn(List.of(new Game(1, "Dota"), new Game(2, "Rust")));
        mvc.perform(get("/api/admin/games/")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[1].name").value("Rust"));
    }

    @Test
    void shouldDeleteGame() throws Exception {
        log.info("Execute test AdminGameRestControllerTest shouldDeleteGame()");
        when(gameService.getAll()).thenReturn(List.of(new Game(1, "Dota")));
        mvc.perform(delete("/api/admin/games/{id}",1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
