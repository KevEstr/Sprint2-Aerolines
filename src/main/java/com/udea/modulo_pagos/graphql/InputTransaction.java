package com.udea.modulo_pagos.graphql;

import com.udea.modulo_pagos.entities.Booking;
import com.udea.modulo_pagos.entities.Payment;
import com.udea.modulo_pagos.entities.PaymentMethod;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Data
public class InputTransaction {

    private byte status;
    private LocalDate date;
    private Float total_price;
    private Float additional_charge;

    private Long booking_id;  // Cambiado de Booking a Long para manejar el ID
    private Long payment_method_id;

    public Long getBooking() {
        return booking_id;
    }

    public void setBooking(Long booking) {
        this.booking_id = booking;
    }

    public Long getPayment_method() {
        return payment_method_id;
    }

    public void setPayment_method(Long payment_method) {
        this.payment_method_id = payment_method;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTotal_price(Float total_price) {
        this.total_price = total_price;
    }

    public void setAdditional_charge(Float additional_charge) {
        this.additional_charge = additional_charge;
    }
}
