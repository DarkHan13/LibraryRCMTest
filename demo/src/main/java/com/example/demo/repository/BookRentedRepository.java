package com.example.demo.repository;

import com.example.demo.entity.BookRented;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRentedRepository extends JpaRepository<BookRented, Long> {



    List<BookRented> findAllByUserOrderByIdDesc(User user);

    List<BookRented>findAllByOrderByIdDesc();


    Optional<BookRented> findBookRentedById(Long bookId);

    Optional<BookRented> findBookRentedByBookIdAndUser(Long bookId, User user);


}
