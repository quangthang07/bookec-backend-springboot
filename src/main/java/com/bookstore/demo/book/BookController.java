package com.bookstore.demo.book;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/book")
public class BookController {

  private final BookService bookService;

  @GetMapping("/page/{index}")
  public List<Book> getBooksPerPage(@PathVariable int index) {
    Pageable pageable = PageRequest.of(index, 6,
                                       Sort.by("name").ascending());
    return bookService.getBooksPerPage(pageable).toList();
  }
  @GetMapping("/{id}")
  public Book getBookById(@PathVariable BigInteger id) {
    return bookService.getBookById(id);
  }
  @GetMapping("/filter")
  public List<Book> filter(@RequestBody QueryRequest request) {
    List<Book> books = new ArrayList<Book>();
    //query by title
    if (request.getTitle() != null) {
      books = bookService.findBooksByTitle(request.getTitle());
    }
    // query by category
    if (request.getCategory() != null) {
      if (books.size() == 0) {
        books = bookService.findBooksByCategory(request.getCategory());
      }else {
        books.retainAll(bookService.findBooksByCategory(request.getCategory()));
      }
    }
    // query by publisher
    if (request.getPublisher() != null) {
      if (books.size() == 0) {
        books = bookService.findBooksByPublisher(request.getPublisher());
      }else {
        books.retainAll(bookService.findBooksByPublisher(request.getPublisher()));
      }
    }
    // query by min price
    if (request.getMinPrice() != null) {
      if (books.size() == 0) {
        books = bookService.findBooksByMinPrice(request.getMinPrice().doubleValue());
      } else {
        books.retainAll(bookService.findBooksByMinPrice(request.getMinPrice().doubleValue()));
      }
    }
    // query by max price
    if (request.getMaxPrice() != null) {
      if (books.size() == 0) {
        books = bookService.findBooksByMaxPrice(request.getMaxPrice().doubleValue());
      } else {
        books.retainAll(bookService.findBooksByMaxPrice(request.getMaxPrice().doubleValue()));
      }
    }
    return books;
  }
  @GetMapping("/")
  public List<Book> search(@RequestParam("search") String text) {
    return this.bookService.search(text, false);
  }

  @PostMapping
  public ResponseEntity<Object> saveNewBook(@RequestBody CRUDRequest request) {
    if (request == null) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Bad request.");
    }
    var newBook = Book.builder()
      .title(request.getTitle())
      .description(request.getDescription())
      .category(request.getCategory())
      .authors(request.getAuthors())
      .publisher(request.getPublisher())
      .price(request.getPrice())
      .stocks(request.getStocks())
      .soldAmount(0)
      .pages(request.getPages())
      .build();
    if (bookService.saveNewBook(newBook) == 0) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Already existed.");
    }
    return ResponseEntity.ok("Successful");
  }

  @PutMapping
  public ResponseEntity<Object> updateBook(@RequestBody CRUDRequest request) {
    if (request == null || request.getId() == null) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Bad request.");
    }
    var modifiedBook = Book.builder()
      .id(request.getId())
      .title(request.getTitle())
      .description(request.getDescription())
      .category(request.getCategory())
      .authors(request.getAuthors())
      .publisher(request.getPublisher())
      .price(request.getPrice())
      .stocks(request.getStocks())
      .soldAmount(request.getSoldAmount())
      .pages(request.getPages())
      .build();
    if (this.bookService.updateBook(modifiedBook) == 0) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Book not found.");
    }
    return ResponseEntity.ok("Successful");
  }

  @DeleteMapping
  public ResponseEntity<Object> deleteBook(@RequestBody CRUDRequest request) {
    if (request == null || request.getId() == null) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Bad request.");
    }
    if (bookService.deleteBook(request.getId()) == 0) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Book not found.");
    }
    return ResponseEntity.ok("Successful");
  }
}
