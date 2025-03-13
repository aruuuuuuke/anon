package com.example.demo.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Chat {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
}