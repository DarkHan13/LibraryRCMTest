package com.example.demo.services;

import com.example.demo.dto.LibraryDTO;
import com.example.demo.entity.Library;
import com.example.demo.entity.User;
import com.example.demo.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    private final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public Library createLibrary(LibraryDTO libraryDTO){
        Library library = new Library();
        library.setAddress(libraryDTO.getAddress());
        library.setName(libraryDTO.getName());
        library.setAddress(libraryDTO.getAddress());
        return libraryRepository.save(library);
    }

    public void deleteLibrary(Long libraryId) {
        Optional<Library> library = libraryRepository.findLibraryById(libraryId);
        library.ifPresent(libraryRepository::delete);
    }

    public Library getLibraryById(Long id){
        return libraryRepository.findLibraryById(id)
                .orElseThrow(() -> new RuntimeException());
    }

    public List<Library> getAllLibrary(){
        return libraryRepository.findAllByOrderByIdDesc();
    }
}
