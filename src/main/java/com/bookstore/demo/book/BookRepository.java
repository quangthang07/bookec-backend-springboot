package com.bookstore.demo.book;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, BigInteger> {

  public List<Book> findByTitle(String name);

  public List<Book> findByCategory(String category);

  public List<Book> findByPublisher(String publisher);

  @Query("{'price': {$gt: ?0}}")
  public List<Book> findByMinPrice(double minPrice);

  @Query("{'price': {$lt: ?0}}")
  public List<Book> findByMaxPrice(double maxPrice);

  @Query("{ $text: { $search: ?0, $caseSensitive: ?1} }")
  public List<Book> search(String text, boolean caseSensitive);

  public List<Book> findAllBy(TextCriteria criteria, org.springframework.data.domain.Sort sort);

  @Query("{'title': ?0, 'category': ?1, 'publisher': ?2, 'authors': ?3 'price': ?4, 'pages': ?5}")
  public Book findBy(String title, String category, String publisher, List<String> authors, double price, int pages);

}
