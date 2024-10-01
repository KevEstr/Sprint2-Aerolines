package com.udea.modulo_pagos.entities;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean is_paid;
    @Column
    private float price;


    public Booking(Long id, boolean is_paid, float price) {
        this.id = id;
        this.is_paid = is_paid;
        this.price = price;
    }

    public void setIsPaid(boolean is_paid) {
        this.is_paid = is_paid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Booking(){

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}


