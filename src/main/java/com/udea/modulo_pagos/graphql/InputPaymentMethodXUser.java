package com.udea.modulo_pagos.graphql;

public class InputPaymentMethodXUser {

    private String card_number;
    private String card_type;
    private String expiry_date;
    private String ccv;

    private Long payment_method_id;
    private Long user_id;

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public Long getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(Long payment_method_id) {
        this.payment_method_id = payment_method_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
