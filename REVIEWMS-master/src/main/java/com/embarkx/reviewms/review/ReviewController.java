package com.embarkx.reviewms.review;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.embarkx.reviewms.review.messaging.ReviewMessageProducer;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

	private ReviewService reviewService;
	// related to RabitMQ
	private ReviewMessageProducer reviewMessageProducer;

	public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
		this.reviewService = reviewService;
		this.reviewMessageProducer = reviewMessageProducer;
	}

	@GetMapping
	public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
		return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {

		boolean isReviewSaved = reviewService.addReview(companyId, review);
		if (isReviewSaved) {
			// related to rabbitMq
			reviewMessageProducer.sendMessage(review);
			return new ResponseEntity<>("Review Added Successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Review NOT-Added Successfully", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{reviewId}")
	public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {

		return new ResponseEntity<>(reviewService.getReviewById(reviewId), HttpStatus.OK);
	}

	@PutMapping("/{reviewId}")
	public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
		boolean isReviewUpdated = reviewService.updateReview(reviewId, review);
		// reviewService.updateReview(review);
		if (isReviewUpdated) {
			return new ResponseEntity<>("Review updated Successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Review not updated Successfully", HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
		boolean isReviewDeleted = reviewService.deleteReview(reviewId);
		if (isReviewDeleted) {
			return new ResponseEntity<>("Review deleted Successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Review not deleted Successfully", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/averageRating")
	public Double getAverageReview(@RequestParam Long companyId) {
		List<Review> reviews = reviewService.getAllReviews(companyId);
		return reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0);

	}

}
