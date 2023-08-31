package com.bookstore.demo.author;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {

  private final AuthorRepository authorRepository;

  public List<Author> getAlAuthors() {
    return authorRepository.findAll();
  }

  public Author getAuthorByFullName(String fullName) {
    return authorRepository.findByFullName(fullName);
  }

  public Author getAuthorById(BigInteger id) {
    return authorRepository.findById(id).get();
  }

  public List<BigInteger> getAllBook(BigInteger id) {
    Author author = authorRepository.findById(id).get();
    return author.getBooks();
  }

  public int saveNewAuthor(Author author) {
    Author existedAuthor = authorRepository.findByFullName(author.getFullName());
    if (existedAuthor != null) {
      return 0;
    }
    authorRepository.save(author);
    return 1;
  }

  public int updateAuthor(Author author) {
    Optional<Author> existedAuthor = authorRepository.findById(author.getId());
    if (existedAuthor.isPresent()) {
      authorRepository.save(author);
      return 1;
    }
    return 0;
  }

  public int deleteAuthor(BigInteger id) {
    Optional<Author> existedAuthor = this.authorRepository.findById(id);
    if(existedAuthor.isPresent()) {
      this.authorRepository.delete(existedAuthor.get());
      return 1;
    }
    return 0;
  }
}
