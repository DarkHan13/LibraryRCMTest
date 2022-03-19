package com.example.demo.services;


import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Book;
import com.example.demo.entity.BookRented;
import com.example.demo.entity.User;
import com.example.demo.entity.enums.ERole;
import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.repository.BookRentedRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class BookRentedService {

    @Autowired
    private final BookRentedRepository bookRentedRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookRentedService(BookRentedRepository bookRentedRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.bookRentedRepository = bookRentedRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public BookRented rentBook(Principal principal , Long bookId) throws Exception {
        User user = getUserByPrincipal(principal);
        Book book = bookRepository.findBookById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id " + bookId));

        if (book.getIsRented()){
            throw new Exception();
        }
        BookRented bookRented =new BookRented();
        bookRented.setBookId(bookId);
        bookRented.setUser(user);
        book.setIsRented(true);
        bookRepository.save(book);
        return bookRentedRepository.save(bookRented);
    }

    public void delete(Principal principal, Long bookId) throws Exception {

        User user = getUserByPrincipal(principal);
        BookRented bookRented = bookRentedRepository.findBookRentedByBookIdAndUser(bookId, user)
                .orElseThrow(() -> new BookNotFoundException("Rented book cannot found with id " + bookId));
        Book book = bookRepository.findBookById(bookRented.getBookId())
                .orElseThrow(() -> new BookNotFoundException("book cannot found with id " + bookRented.getBookId()));

        book.setIsRented(false);
        bookRepository.save(book);

        bookRentedRepository.delete(bookRented);


    }

    public List<BookRented> getAllRentedBooks() {
        return bookRentedRepository.findAllByOrderByIdDesc();}

    public List<BookRented> getAllRentedBooksForUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return bookRentedRepository.findAllByUserOrderByIdDesc(user);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }


}
