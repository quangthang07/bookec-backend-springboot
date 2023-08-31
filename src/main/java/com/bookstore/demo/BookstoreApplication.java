package com.bookstore.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bookstore.demo.book.Book;
import com.bookstore.demo.book.BookService;
import com.bookstore.demo.user.Role;
import com.bookstore.demo.user.User;
import com.bookstore.demo.user.UserService;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserService userService, BookService bookService) {
		return args -> {
			User user = User.builder()
				.firstName("Admin")
				.lastName("Admin")
				.email("admin@gmail.com")
				.password("admin123")
				.role(Role.ADMIN)
				.build();
			userService.addNewUser(user);

			Book book1 = Book.builder()
			.title("The Art of not giving a fuck")
			.description("")
			.category("self-help")
			.publisher("publisher1")
			.authors(List.of("Mark Manson"))
			.price(17.9)
			.stocks(100)
			.soldAmount(0)
			.pages(160).build();
			bookService.saveNewBook(book1);

			Book book2 = Book.builder()
			.title("Atomic habits")
			.description("")
			.category("self-help")
			.publisher("publisher2")
			.authors(List.of("Morgan Housel"))
			.price(12.9)
			.stocks(100)
			.soldAmount(0)
			.pages(158).build();
			bookService.saveNewBook(book2);

			Book book3 = Book.builder()
			.title("Design Pattern")
			.description("")
			.category("programming")
			.publisher("publisher3")
			.authors(List.of("Eric Freeman","Elisobeth Robson"))
			.price(47.9)
			.stocks(100)
			.soldAmount(0)
			.pages(590).build();
			bookService.saveNewBook(book3);

			Book book4 = Book.builder()
			.title("Object Oriented Programming")
			.description("")
			.category("programming")
			.publisher("publisher3")
			.authors(List.of("Eric Freeman","Elisobeth Robson"))
			.price(45.9)
			.stocks(100)
			.soldAmount(0)
			.pages(480).build();
			bookService.saveNewBook(book4);
		};
	}
}
