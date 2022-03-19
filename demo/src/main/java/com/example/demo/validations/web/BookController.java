package com.example.demo.validations.web;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;
import com.example.demo.entity.BookRented;
import com.example.demo.entity.Library;
import com.example.demo.facade.BookFacade;
import com.example.demo.repository.LibraryRepository;
import com.example.demo.repository.responce.MessageResponce;
import com.example.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.PrivateKey;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("api/book")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookFacade bookFacade;
    @Autowired
    private LibraryRepository libraryRepository;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/{libraryId}/create")
    public ResponseEntity<BookDTO> createBook(@PathVariable("libraryId") String libraryId,@Valid @RequestBody BookDTO bookDTO){
        Book book = bookService.createBook(bookDTO, Long.parseLong(libraryId));
        BookDTO createdBook = bookFacade.bookToBookDTO(book);
        return new ResponseEntity<>(createdBook, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/{bookId}/delete")
    public ResponseEntity<Object> deleteBook(@PathVariable("bookId") String bookId) {
        bookService.deleteBook(Long.parseLong(bookId));
        return new ResponseEntity<>(new MessageResponce("Book was deleted"), HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<List<BookDTO>> getAllBook() {
        List<BookDTO> bookDTOList =  bookService.getAllBooks().stream().map(bookFacade::bookToBookDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookDTOList, HttpStatus.OK);
    }

    @PostMapping("/{libraryId}/books")
    public ResponseEntity<List<BookDTO>> getAllBookForLibrary(@PathVariable("libraryId") String libraryId){
        Library library = libraryRepository.findLibraryById(Long.parseLong(libraryId))
                .orElseThrow(() -> new RuntimeException());
        List<BookDTO> bookDTOList = bookService.getAllBooksForLibrary(library)
                .stream().map(bookFacade::bookToBookDTO).collect(Collectors.toList());

        return new ResponseEntity<>(bookDTOList, HttpStatus.OK);
    }

    @PostMapping("/{name}")
    public ResponseEntity<List<BookDTO>> getBookByName(@PathVariable("name") String name){
        List<BookDTO> bookDTOList = bookService.getBooksByName(name)
                .stream().map(bookFacade::bookToBookDTO).collect(Collectors.toList());

        return new ResponseEntity<>(bookDTOList, HttpStatus.OK);
    }
}
