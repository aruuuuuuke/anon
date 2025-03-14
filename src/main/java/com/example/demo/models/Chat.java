package com.example.demo.models;
import jakarta.persistence.*;

@Entity
@Table
public class Chat {
    @Id
    @GeneratedValue
    private Long id;

    private String name;


    @ManyToOne
    @JoinColumn(name = "creater_id", nullable = false)
    private User user;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}