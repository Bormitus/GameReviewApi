package com.gamereview.api.repositories;

import com.gamereview.api.models.Game;
import com.gamereview.api.models.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class ReviewRepositoryTests {

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void ReviewRepository_SaveAll_ReturnsSavedReview() {

        Review review = Review.builder().title("Review Title").content("Review Content").rating(10).build();

        Review savedReview = reviewRepository.save(review);

        Assertions.assertNotNull(savedReview);
        Assertions.assertTrue(savedReview.getId() > 0);
    }

    @Test
    public void ReviewRepository_GetAll_ReturnsMoreThanOneReview() {
        Review review = Review.builder().title("Review Title").content("Review Content").rating(10).build();
        Review review2 = Review.builder().title("Review Title 2").content("Review Content 2").rating(10).build();

        Review savedReview = reviewRepository.save(review);
        Review savedReview2 = reviewRepository.save(review2);

        List<Review> allReviews = reviewRepository.findAll();

        Assertions.assertNotNull(allReviews);
        Assertions.assertTrue(allReviews.size() == 2);
    }

    @Test
    public void ReviewRepository_FindById_ReturnsReview() {
        Review review = Review.builder().title("Review Title").content("Review Content").rating(10).build();

        Review savedReview = reviewRepository.save(review);

        Review reviewById = reviewRepository.findById(savedReview.getId()).get();

        Assertions.assertNotNull(reviewById);
    }

    @Test
    public void ReviewRepository_Update_ReturnsUpdatedReview() {
        Review review1 = Review.builder().title("Review Title").content("Review Content").rating(10).build();

        Review savedReview = reviewRepository.save(review1);

        Review review = reviewRepository.findById(savedReview.getId()).get();

        review.setTitle("Review Title 2");
        review.setContent("Review Description 2");
        review.setRating(5);

        Review updatedReview = reviewRepository.save(review);

        Assertions.assertNotNull(updatedReview);
        Assertions.assertNotNull(updatedReview.getTitle());
        Assertions.assertNotNull(updatedReview.getContent());
        Assertions.assertNotNull(updatedReview.getRating());

    }

    @Test
    public void ReviewRepository_Delete_ReturnsNull() {
        Review review1 = Review.builder().title("Review Title").content("Review Content").rating(10).build();

        reviewRepository.save(review1);
        reviewRepository.deleteById(review1.getId());

        Optional<Review> optionalReview = reviewRepository.findById(review1.getId());

        Assertions.assertFalse(optionalReview.isPresent());
    }
}
