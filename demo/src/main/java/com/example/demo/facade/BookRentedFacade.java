package com.example.demo.facade;

import com.example.demo.dto.BookRentedDTO;
import com.example.demo.entity.BookRented;
import org.springframework.stereotype.Component;

@Component
public class BookRentedFacade {

    public BookRentedDTO bookRentedToBookRentedDTO(BookRented bookRented){
        BookRentedDTO bookRentedDTO = new BookRentedDTO();
        bookRentedDTO.setId(bookRented.getId());
        bookRentedDTO.setBookId(bookRented.getBookId());

        return bookRentedDTO;
    }
}
