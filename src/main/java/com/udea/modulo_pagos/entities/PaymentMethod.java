package com.udea.modulo_pagos.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="payment_method")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public PaymentMethod() {
    }

    public PaymentMethod(Long id, String name, String description) {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


}
