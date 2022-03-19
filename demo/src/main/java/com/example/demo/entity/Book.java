package com.example.demo.entity;


import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean isRented;
    @ManyToOne(fetch = FetchType.LAZY)
    private Library library;
}
