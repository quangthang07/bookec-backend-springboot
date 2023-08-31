package com.bookstore.demo.author;

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
@RequestMapping("/api/v1/author")
public class AuthorController {

  private final AuthorService authorService;

  @GetMapping
  public List<Author> getAllAuthor() {
    return this.authorService.getAlAuthors();
  }

  @GetMapping("/{id}")
  public Author getById(@PathVariable BigInteger id) {
    return authorService.getAuthorById(id);
  }

  @PostMapping
  public ResponseEntity<Object> saveNewAuthor(@RequestBody CRUDRequest request) {
    if (request == null) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Bad request.");
    }
    Author newAuthor = Author.builder()
      .fullName(request.getFullName())
      .books(request.getBooks())
      .build();
    if (authorService.saveNewAuthor(newAuthor) == 0) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Author already existed.");
    }
    return ResponseEntity.ok("Successful");
  }

  @PutMapping
  public ResponseEntity<Object> updateAuthor(@RequestBody CRUDRequest request) {
    if (request == null || request.getId() == null) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Bad request.");
    }
    Author author = Author.builder()
      .id(request.getId())
      .fullName(request.getFullName())
      .books(request.getBooks())
      .build();
    if (authorService.updateAuthor(author) == 0) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Author not found.");
    }
    return ResponseEntity.ok("Successful");
  }

  @DeleteMapping
  public ResponseEntity<Object> deleteAuthor(@RequestBody CRUDRequest request) {
    if (request == null || request.getId() == null) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Bad request.");
    }
    if (authorService.deleteAuthor(request.getId()) == 0) {
      return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("Author not found.");
    }
    return ResponseEntity.ok("Successful");
  }
}
