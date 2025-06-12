package com.gamereview.api.controllers;

import com.gamereview.api.dtos.ReviewDto;
import com.gamereview.api.services.interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("game/{gameId}/review")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReviewDto> createReview(@PathVariable("gameId") int id, @RequestBody ReviewDto reviewdto) {
        return new ResponseEntity<>(reviewService.createReview(id, reviewdto), HttpStatus.CREATED);
    }

    @GetMapping("game/{gameId}/review")
    public ResponseEntity<List<ReviewDto>> getReviewsByGameId(@PathVariable("gameId") int id) {
        return new ResponseEntity<>(reviewService.getReviewsByGameId(id), HttpStatus.OK);
    }

    @GetMapping("game/{gameId}/review/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable("gameId") int gameId, @PathVariable("reviewId") int reviewId) {
        return new ResponseEntity<>(reviewService.getReviewById(reviewId, gameId), HttpStatus.OK);
    }

    @PutMapping("game/{gameId}/review/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable("gameId") int gameId, @PathVariable("reviewId") int reviewId, @RequestBody ReviewDto reviewdto) {
        return new ResponseEntity<>(reviewService.updateReview(reviewId, gameId, reviewdto), HttpStatus.OK);
    }

    @DeleteMapping("game/{gameId}/review/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable("gameId") int gameId, @PathVariable("reviewId") int reviewId) {
        reviewService.deleteReview(reviewId, gameId);

        return new ResponseEntity<>("Review was deleted successfully", HttpStatus.OK);
    }
}
