package com.bookstore.demo.review;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bookstore.demo.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;

  public List<Review> getAllReviewByUser(String user) {
    return reviewRepository.findByUser(user);
  }

  public List<Review> getAllReviewByBook(BigInteger bookId) {
    return reviewRepository.findByBookId(bookId);
  }

  public boolean saveNewReview(Review review) {
    if (checkIsValidUser(review.getUser())) {
      reviewRepository.save(review);
      return true;
    }
    return false;
  }

  public boolean updateReview(Review review) {
    Optional<Review> existedReview = reviewRepository.findById(review.getId());
    if(existedReview.isPresent() && checkIsValidUser(review.getUser())) {
      reviewRepository.save(review);
      return true;
    }
    return false;
  }

  public boolean deleteReview(BigInteger id, String user) {
    Optional<Review> existedReview = reviewRepository.findById(id);
    if (existedReview.isPresent() && checkIsValidUser(user)) {
      reviewRepository.delete(existedReview.get());
      return true;
    }
    return false;
  }

  private boolean checkIsValidUser(String email) {
    User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (user.getEmail().equals(email)) {
      return true;
    }
    return false;
  }
}
