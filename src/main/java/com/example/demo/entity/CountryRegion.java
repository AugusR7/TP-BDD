package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
public class CountryRegion {
    @Id
    private Integer id;

}
