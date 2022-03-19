package com.example.demo.validations.web;

import com.example.demo.dto.BookRentedDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.BookRented;
import com.example.demo.entity.User;
import com.example.demo.facade.BookRentedFacade;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.responce.MessageResponce;
import com.example.demo.services.BookRentedService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/bookRented")
public class BookRentedController {
    @Autowired
    private  BookRentedFacade bookRentedFacade;
    @Autowired
    private BookRentedService bookRentedService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{bookId}/rent")
    public ResponseEntity<Object> restBook(@PathVariable("bookId") String bookId,Principal principal){
        BookRented bookRented;
        try {
            bookRented = bookRentedService.rentBook(principal, Long.parseLong(bookId));
        } catch (Exception e){
            return (ResponseEntity<Object>) ResponseEntity.badRequest();
        }
        BookRentedDTO bookRentedDTO = bookRentedFacade.bookRentedToBookRentedDTO(bookRented);
        return new ResponseEntity<>(bookRentedDTO, HttpStatus.OK);
    }

    @PostMapping("/{bookId}/return")
    public ResponseEntity<MessageResponce> returnBook(@PathVariable("bookId") String bookRentedId, Principal principal){
        try {
            bookRentedService.delete(principal, Long.parseLong(bookRentedId));
        } catch (Exception e){
            return (ResponseEntity<MessageResponce>) ResponseEntity.badRequest();
        }
        return new ResponseEntity<>(new MessageResponce("return book successfully"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/all")
    public ResponseEntity<List<BookRentedDTO>> getAllBookRented() {
        List<BookRentedDTO> bookRentedDTOList = bookRentedService.getAllRentedBooks()
                .stream().map(bookRentedFacade::bookRentedToBookRentedDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(bookRentedDTOList, HttpStatus.OK);
    }

    @PostMapping("/user/all")
    public ResponseEntity<List<BookRentedDTO>> getAllBookForUser(Principal principal) {
        List<BookRentedDTO> bookRentedDTOList = bookRentedService.getAllRentedBooksForUser(principal)
                .stream().map(bookRentedFacade::bookRentedToBookRentedDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(bookRentedDTOList, HttpStatus.OK);
    }


}
