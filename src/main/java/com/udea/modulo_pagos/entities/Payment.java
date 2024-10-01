package com.udea.modulo_pagos.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name="payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payment_id;
    @Getter
    private LocalDate date;

    public Payment(){}

    public Payment(Long payment_id, LocalDate date, Transaction transaction, GatewayPayment gatewayPayment) {
        this.payment_id = payment_id;
        this.date = date;
        this.transaction = transaction;
        this.gatewayPayment = gatewayPayment;
    }

    @Getter
    @ManyToOne
    @JoinColumn(name="transaction_id", nullable = false)
    private Transaction transaction;

    @Getter
    @ManyToOne
    @JoinColumn(name="gateway_payment_id", nullable = false)
    private GatewayPayment gatewayPayment ;

    public Long getId() {
        return payment_id;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setGatewayPayment(GatewayPayment gatewayPayment) {
        this.gatewayPayment = gatewayPayment;
    }

    public void setId(Long payment_id) {
        this.payment_id = payment_id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
