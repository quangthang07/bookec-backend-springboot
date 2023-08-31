package com.bookstore.demo.review;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

  private final ReviewService reviewService;

  @GetMapping("/{bookId}")
  public List<Review> getAllReviewByBookId(@PathVariable BigInteger bookId) {
    return reviewService.getAllReviewByBook(bookId);
  }

  @PostMapping
  public ResponseEntity<Object> saveNewReview(@RequestBody ReviewRequest request) {
    if (request == null || request.getBookId() == null || request.getUser() == null) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Bad Request");
    }
    Review review = Review.builder()
      .bookId(request.getBookId())
      .user(request.getUser())
      .rate(request.getRate())
      .content(request.getContent())
      .build();

    if (reviewService.saveNewReview(review)) {
      return ResponseEntity.ok("Successful");
    }
    return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();

  }

  @PutMapping
  public ResponseEntity<Object> updateReview(@RequestBody ReviewRequest request) {
    if (request == null || request.getId() == null) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Bad Request");
    }
    Review review = Review.builder()
      .id(request.getId())
      .bookId(request.getBookId())
      .user(request.getUser())
      .rate(request.getRate())
      .content(request.getContent())
      .build();
    if (reviewService.updateReview(review)) {
      return ResponseEntity.ok("Successful");
    }
    return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(
      "Review not found or you are not allowed to delete another user's review."
    );
  }

  @DeleteMapping
  public ResponseEntity<Object> deleteReview(@RequestBody ReviewRequest request) {
    if (request == null || request.getId() == null || request.getUser() == null) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Bad Request");
    }
    if (reviewService.deleteReview(request.getId(), request.getUser())) {
      return ResponseEntity.ok("Successful");
    }
    return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(
      "Review not found or you are not allowed to delete another user's review."
    );
  }
}
