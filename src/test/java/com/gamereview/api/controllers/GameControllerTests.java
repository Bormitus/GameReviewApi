package com.gamereview.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamereview.api.dtos.GameDto;
import com.gamereview.api.dtos.GameResponseDto;
import com.gamereview.api.dtos.ReviewDto;
import com.gamereview.api.models.Game;
import com.gamereview.api.models.Review;
import com.gamereview.api.services.interfaces.GameService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = GameController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class GameControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private GameService gameService;

    private Game game;
    private GameDto gameDto;

    @BeforeEach
    public void init() {
        game = Game.builder().title("Game Title 1").description("Game Description 1").build();
        gameDto = GameDto.builder().title(game.getTitle()).description(game.getDescription()).build();
    }

    @Test
    public void GameController_CreateGame_ReturnsCreated() throws Exception {
        given(gameService.createGame(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/game/create")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(gameDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(gameDto.getDescription())));
    }

    @Test
    public void GameController_GetAll_ReturnsResponseDto() throws Exception {
        GameResponseDto responseDto = GameResponseDto.builder().pageNumber(1).pageSize(10).last(true).content(Arrays.asList(gameDto)).build();
        when(gameService.getAllGames(1, 10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/game")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNumber", "1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    public void GameController_GetGameById_ReturnsGameDto() throws Exception {
        when(gameService.getGameById(1)).thenReturn(gameDto);

        ResultActions response = mockMvc.perform(get("/api/game/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(gameDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(gameDto.getDescription())));
    }

    @Test
    public void GameController_UpdateGame_ReturnsGameDto() throws Exception {
        when(gameService.updateGame(gameDto, 1)).thenReturn(gameDto);

        ResultActions response = mockMvc.perform(put("/api/game/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(gameDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(gameDto.getDescription())));
    }

    @Test
    public void GameController_DeleteGame_ReturnsString() throws Exception {
        doNothing().when(gameService).deleteGameById(1);

        ResultActions response = mockMvc.perform(delete("/api/game/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
