package com.bookstore.demo.author;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends MongoRepository<Author, BigInteger>{

  public Author findByFullName(String fullName);
}
