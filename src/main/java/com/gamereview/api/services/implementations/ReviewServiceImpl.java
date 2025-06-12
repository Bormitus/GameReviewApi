package com.gamereview.api.services.implementations;

import com.gamereview.api.dtos.ReviewDto;
import com.gamereview.api.exceptions.GameNotFoundException;
import com.gamereview.api.exceptions.ReviewNotFoundException;
import com.gamereview.api.models.Game;
import com.gamereview.api.models.Review;
import com.gamereview.api.repositories.GameRepository;
import com.gamereview.api.repositories.ReviewRepository;
import com.gamereview.api.services.interfaces.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private GameRepository gameRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, GameRepository gameRepository) {
        this.reviewRepository = reviewRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public ReviewDto createReview(int gameId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);

        Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game could not be found"));

        review.setGame(game);
        Review newReview = reviewRepository.save(review);

        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByGameId(int id) {
        List<Review> reviews = reviewRepository.findByGameId(id);

        return reviews.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game could not be found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review could not be found"));

        if(review.getGame().getId() != game.getId()){
            throw new ReviewNotFoundException("Review does not belong to the Game");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int reviewId, int gameId, ReviewDto reviewDto) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game could not be found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review could not be found"));

        if(review.getGame().getId() != game.getId()){
            throw new ReviewNotFoundException("Review does not belong to the Game");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getRating());

        Review updatedReview = reviewRepository.save(review);

        return mapToDto(updatedReview);
    }

    @Override
    public void deleteReview(int reviewId, int gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game could not be found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review could not be found"));

        if(review.getGame().getId() != game.getId()){
            throw new ReviewNotFoundException("Review does not belong to the Game");
        }

        reviewRepository.delete(review);
    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setContent(review.getContent());
        dto.setTitle(review.getTitle());
        dto.setRating(review.getRating());

        return dto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setContent(reviewDto.getContent());
        review.setTitle(reviewDto.getTitle());
        review.setRating(reviewDto.getRating());

        return review;
    }
}
