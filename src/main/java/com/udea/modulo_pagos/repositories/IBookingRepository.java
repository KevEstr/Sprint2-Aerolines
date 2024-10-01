package com.udea.modulo_pagos.repositories;

import com.udea.modulo_pagos.entities.Booking;
import org.springframework.data.repository.CrudRepository;

public interface IBookingRepository extends CrudRepository<Booking, Long> {
}
