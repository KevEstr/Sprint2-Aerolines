package com.udea.modulo_pagos.service;

import com.udea.modulo_pagos.entities.Booking;

public interface IBookingService {

    Booking getBookingById(Long id);

    Booking updateBookingStatus(Long bookingId, boolean isPaid);
}
