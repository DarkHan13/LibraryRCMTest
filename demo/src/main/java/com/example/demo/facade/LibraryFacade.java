package com.example.demo.facade;

import com.example.demo.dto.LibraryDTO;
import com.example.demo.entity.Library;
import org.springframework.stereotype.Component;

@Component
public class LibraryFacade {

    public LibraryDTO libraryToLibraryDTO(Library library){
        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setAddress(library.getAddress());
        libraryDTO.setId(library.getId());
        libraryDTO.setName(library.getName());
        return libraryDTO;
    }
}
