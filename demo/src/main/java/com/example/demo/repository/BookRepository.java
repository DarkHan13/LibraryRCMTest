package com.example.demo.repository;

import com.example.demo.entity.Book;
import com.example.demo.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByOrderByIdDesc();

    List<Book> findAllByLibraryOrderByIdDesc(Library library);

    List<Book> findAllByNameOrderByIdDesc(String name);

    Optional<Book> findBookById(Long bookId);
}
