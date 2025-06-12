package com.gamereview.api.services.interfaces;

import com.gamereview.api.dtos.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(int gameId, ReviewDto reviewDto);
    List<ReviewDto> getReviewsByGameId(int id);
    ReviewDto getReviewById(int reviewId, int gameId);
    ReviewDto updateReview(int reviewId, int gameId, ReviewDto reviewDto);
    void deleteReview(int reviewId, int gameId);
}
