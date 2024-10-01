package com.udea.modulo_pagos.entities;

import jakarta.persistence.*;

@Entity
@Table(name="flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
