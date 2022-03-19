package com.example.demo.services;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;
import com.example.demo.entity.BookRented;
import com.example.demo.entity.Library;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LibraryRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LibraryRepository libraryRepository;

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository, LibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.libraryRepository = libraryRepository;
    }

    public Book createBook(BookDTO bookDTO, Long libraryId){
        Library library = libraryRepository.getById(libraryId);
        Book book = new Book();
        book.setLibrary(library);
        book.setName(bookDTO.getName());
        book.setIsRented(false);

        return bookRepository.save(book);
    }
    public void deleteBook(Long bookId){
        Optional<Book> book = bookRepository.findBookById(bookId);
        book.ifPresent(bookRepository::delete);
    }

    public List<Book> getAllBooks() {return bookRepository.findAllByOrderByIdDesc();}

    public List<Book> getAllBooksForLibrary(Library library) {
        return bookRepository.findAllByLibraryOrderByIdDesc(library);
    }

    public List<Book> getBooksByName(String name) {
        return bookRepository.findAllByNameOrderByIdDesc(name);

    }


}
