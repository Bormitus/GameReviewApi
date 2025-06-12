package com.gamereview.api.services;

import com.gamereview.api.dtos.GameDto;
import com.gamereview.api.dtos.ReviewDto;
import com.gamereview.api.models.Game;
import com.gamereview.api.models.Review;
import com.gamereview.api.repositories.GameRepository;
import com.gamereview.api.repositories.ReviewRepository;
import com.gamereview.api.services.implementations.ReviewServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private GameRepository gameRepository;
    @InjectMocks
    private ReviewServiceImpl reviewServiceImpl;

    private Game game;
    private Review review;
    private GameDto gameDto;
    private ReviewDto reviewDto;

    @BeforeEach
    public void init() {
        game = Game.builder().title("Game Title 1").description("Game Description 1").build();
        gameDto = GameDto.builder().title(game.getTitle()).description(game.getDescription()).build();
        review = Review.builder().title("Review Title").content("Review Content").rating(10).build();
        reviewDto = ReviewDto.builder().title(review.getTitle()).content(review.getContent()).rating(review.getRating()).build();

    }

    @Test
    public void ReviewService_CreateReview_ReturnsReviewDto() {
        when(gameRepository.findById(game.getId())).thenReturn(Optional.ofNullable(game));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto result = reviewServiceImpl.createReview(game.getId(), reviewDto);

        Assertions.assertNotNull(result);
    }

    @Test
    public void ReviewService_GetAllGetReviewsByGameId_ReturnsReviewDtoList()
    {
        when(reviewRepository.findByGameId(game.getId())).thenReturn(Arrays.asList(review));

        List<ReviewDto> result = reviewServiceImpl.getReviewsByGameId(game.getId());

        Assertions.assertNotNull(result);
    }

    @Test
    public void ReviewService_GetReviewById_ReturnsReviewDto() {
        when(gameRepository.findById(game.getId())).thenReturn(Optional.ofNullable(game));
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.ofNullable(review));

        review.setGame(game);

        ReviewDto result = reviewServiceImpl.getReviewById(game.getId(), review.getId());

        Assertions.assertNotNull(result);
    }

    @Test
    public void ReviewService_UpdateReview_ReturnsReviewDto() {
        when(gameRepository.findById(game.getId())).thenReturn(Optional.ofNullable(game));
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.ofNullable(review));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        game.setReviews(Arrays.asList(review));
        review.setGame(game);

        ReviewDto result = reviewServiceImpl.updateReview(review.getId(), game.getId(), reviewDto);

        Assertions.assertNotNull(result);
    }

    @Test
    public void ReviewService_DeleteReview_ReturnsVoid() {
        when(gameRepository.findById(game.getId())).thenReturn(Optional.ofNullable(game));
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.ofNullable(review));

        game.setReviews(Arrays.asList(review));
        review.setGame(game);

        Assertions.assertAll(() -> reviewServiceImpl.deleteReview(review.getId(), game.getId()));
    }
}

