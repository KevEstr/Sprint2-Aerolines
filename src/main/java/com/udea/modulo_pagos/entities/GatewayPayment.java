package com.udea.modulo_pagos.entities;

import jakarta.persistence.*;

@Entity
@Table(name="gateway_payment")
public class GatewayPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public GatewayPayment(){

    }
    public GatewayPayment(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
