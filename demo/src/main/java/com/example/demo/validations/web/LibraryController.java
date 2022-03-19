package com.example.demo.validations.web;

import com.example.demo.dto.LibraryDTO;
import com.example.demo.entity.Library;
import com.example.demo.facade.LibraryFacade;
import com.example.demo.repository.responce.MessageResponce;
import com.example.demo.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("api/library")

public class LibraryController {

    @Autowired
    private LibraryService libraryService;
    @Autowired
    private LibraryFacade libraryFacade;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Object> createLibrary(@Valid @RequestBody LibraryDTO libraryDTO){
        Library library = libraryService.createLibrary(libraryDTO);

        LibraryDTO createdLibrary = libraryFacade.libraryToLibraryDTO(library);

        return new ResponseEntity<>(createdLibrary, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/{libraryId}/delete")
    public ResponseEntity<MessageResponce> deleteLibrary(@PathVariable("libraryId") String libraryId){
        libraryService.deleteLibrary(Long.parseLong(libraryId));
        return new ResponseEntity<>(new MessageResponce("Library was deleted"), HttpStatus.OK);
    }

    @PostMapping("/{libraryId}")
    public ResponseEntity<LibraryDTO> getLibraryById(@PathVariable("libraryId") String libraryId){
        Library library = libraryService.getLibraryById(Long.parseLong(libraryId));
        LibraryDTO libraryDTO = libraryFacade.libraryToLibraryDTO(library);

        return new ResponseEntity<>(libraryDTO, HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<List<LibraryDTO>> getAllLibraries(){
        List<LibraryDTO> libraryDTOList = libraryService.getAllLibrary().stream()
                .map(libraryFacade::libraryToLibraryDTO).collect(Collectors.toList());

        return new ResponseEntity<>(libraryDTOList, HttpStatus.OK);
    }
}
