package com.bookstore.demo.book;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.bookstore.demo.author.Author;
import com.bookstore.demo.author.AuthorService;

import lombok.RequiredArgsConstructor;
import lombok.var;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  private final AuthorService authorService ;

  public Page<Book> getBooksPerPage(Pageable pageable) {
    return this.bookRepository.findAll(pageable);
  }
  //get book by id
  public Book getBookById(BigInteger id) {
    return this.bookRepository.findById(id).orElseThrow(
      () -> new HttpClientErrorException(HttpStatusCode.valueOf(404), "No result")
    );
  }
  // find books by title
  public List<Book> findBooksByTitle(String title) {
    return this.bookRepository.findByTitle(title);
  }
  // find books by category
  public List<Book> findBooksByCategory(String category) {
    return this.bookRepository.findByCategory(category);
  }
  // find books by publisher
  public List<Book> findBooksByPublisher(String publisher) {
    return this.bookRepository.findByPublisher(publisher);
  }
  // find book by min price
  public List<Book> findBooksByMinPrice(double minPrice) {
    return this.bookRepository.findByMinPrice(minPrice);
  }
  // find book by max price
  public List<Book> findBooksByMaxPrice(double maxPrice) {
    return this.bookRepository.findByMaxPrice(maxPrice);
  }

  public List<Book> search(String text, boolean caseSensitive) {
    // return this.bookRepository.search(text, caseSensitive);
    return bookRepository.findAllBy(
      new TextCriteria().matchingAny(text),
      org.springframework.data.domain.Sort.by("score")
    );
  }

  public int saveNewBook(Book book) {
    Book existedBook = bookRepository.findBy(
      book.getTitle(),
      book.getCategory(),
      book.getPublisher(),
      book.getAuthors(),
      book.getPrice(),
      book.getPages()
    );
    if (existedBook == null) {
      this.bookRepository.save(book);
      updateBookListOfAuthor(
        bookRepository.findBy(
          book.getTitle(),
          book.getCategory(),
          book.getPublisher(),
          book.getAuthors(),
          book.getPrice(),
          book.getPages()
        ),
        false);
      return 1;
    }
    return 0;
  }

  public int updateBook(Book book) {
    Optional<Book> existBook = this.bookRepository.findById(book.getId());
    if (existBook.isPresent()) {
      updateBookListOfAuthor(existBook.get(), true);
      this.bookRepository.save(book);
      updateBookListOfAuthor(book, false);
      return 1;
    }
    return 0;
  }

  public int deleteBook(BigInteger id) {
    Optional<Book> existBook = this.bookRepository.findById(id);
    if(existBook.isPresent()) {
      updateBookListOfAuthor(existBook.get(), true);
      this.bookRepository.delete(existBook.get());
      return 1;
    }
    return 0;
  }

  // maybeRemove mean that book maybe removed from book's list in author document
  public void updateBookListOfAuthor(Book book, boolean maybeRemove) {
    for (String a : book.getAuthors()) {
      Author author = authorService.getAuthorByFullName(a);
      if (maybeRemove && author != null) {
        author.getBooks().remove(book.getId());
        authorService.updateAuthor(author);
        continue;
      }

      if (author == null) {
        Author newAuthor = Author.builder()
          .fullName(a)
          .books(List.of(book.getId()))
          .build();
        authorService.saveNewAuthor(newAuthor);
      }else {
        if (!author.getBooks().contains(book.getId())) {
          author.getBooks().add(book.getId());
          authorService.updateAuthor(author);
        }
      }
    }
  }
}
