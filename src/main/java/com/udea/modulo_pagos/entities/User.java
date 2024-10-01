package com.udea.modulo_pagos.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public User(Long id) {
        this.id = id;
    }

    public User(){

    }

    public void setId(Long id) {
        this.id = id;
    }
}
