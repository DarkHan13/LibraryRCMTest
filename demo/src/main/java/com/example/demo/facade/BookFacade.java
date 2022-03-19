package com.example.demo.facade;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookFacade {

    public BookDTO bookToBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setIsRented(book.getIsRented());
        bookDTO.setName(book.getName());
        bookDTO.setLibraryId(book.getLibrary().getId());

        return bookDTO;

    }
}
