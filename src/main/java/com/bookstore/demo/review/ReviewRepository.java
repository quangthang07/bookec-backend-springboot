package com.bookstore.demo.review;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, BigInteger> {

  public List<Review> findByBookId(BigInteger bookId);

  public List<Review> findByUser(String user);

}
