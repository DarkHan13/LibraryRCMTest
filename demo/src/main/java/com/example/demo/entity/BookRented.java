package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BookRented {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long bookId;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
