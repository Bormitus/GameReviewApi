package com.gamereview.api.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamereview.api.dtos.GameDto;
import com.gamereview.api.dtos.ReviewDto;
import com.gamereview.api.models.Game;
import com.gamereview.api.models.Review;
import com.gamereview.api.services.interfaces.GameService;
import com.gamereview.api.services.interfaces.ReviewService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ReviewController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReviewControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    private GameService gameService;

    @MockitoBean
    private ReviewService reviewService;

    private Game game;
    private Review review;
    private ReviewDto reviewDto;
    private GameDto gameDto;

    @BeforeEach
    public void init() {
        game = Game.builder().title("Game Title 1").description("Game Description 1").build();
        gameDto = GameDto.builder().title(game.getTitle()).description(game.getDescription()).build();
        review = Review.builder().title("Review Title").content("Review Content").rating(10).build();
        reviewDto = ReviewDto.builder().title(review.getTitle()).content(review.getContent()).rating(review.getRating()).build();
    }

    @Test
    public void ReviewController_GetReviewsByPokemonId_ReturnReviewDto() throws Exception {
        when(reviewService.getReviewsByGameId(1)).thenReturn(Arrays.asList(reviewDto));

        ResultActions response = mockMvc.perform(get("/api/game/1/review")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(Arrays.asList(reviewDto).size())));
    }

    @Test
    public void ReviewController_UpdateReview_ReturnReviewDto() throws Exception {
        when(reviewService.updateReview(1, 1, reviewDto)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(put("/api/game/1/review/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating", CoreMatchers.is(reviewDto.getRating())));
    }


    @Test
    public void ReviewController_CreateReview_ReturnReviewDto() throws Exception {
        when(reviewService.createReview(1, reviewDto)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(post("/api/game/1/review")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating", CoreMatchers.is(reviewDto.getRating())));
    }

    @Test
    public void ReviewController_GetReviewId_ReturnReviewDto() throws Exception {

        when(reviewService.getReviewById(1, 1)).thenReturn(reviewDto);

        ResultActions response = mockMvc.perform(get("/api/game/1/review/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating", CoreMatchers.is(reviewDto.getRating())));
    }

    @Test
    public void ReviewController_DeleteReview_ReturnOk() throws Exception {

        doNothing().when(reviewService).deleteReview(1, 1);

        ResultActions response = mockMvc.perform(delete("/api/game/1/review/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
