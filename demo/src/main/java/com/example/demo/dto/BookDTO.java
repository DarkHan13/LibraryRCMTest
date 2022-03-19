package com.example.demo.dto;

import com.example.demo.entity.Library;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
public class BookDTO {

    private Long id;
    private String name;
    private Boolean isRented;
    private Long libraryId;
}
