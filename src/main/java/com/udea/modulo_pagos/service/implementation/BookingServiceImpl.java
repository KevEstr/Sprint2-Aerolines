package com.udea.modulo_pagos.service.implementation;

import com.udea.modulo_pagos.entities.Booking;
import com.udea.modulo_pagos.entities.Transaction;
import com.udea.modulo_pagos.repositories.IBookingRepository;
import com.udea.modulo_pagos.repositories.IGatewayPaymentRepository;
import com.udea.modulo_pagos.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public Booking updateBookingStatus(Long bookingId, boolean isPaid){
        var booking = new Booking ();
        booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setIsPaid(isPaid);
        bookingRepository.save(booking);
        return booking;
    }


}
