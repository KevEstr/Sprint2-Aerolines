package com.udea.modulo_pagos.service;

import com.udea.modulo_pagos.entities.User;

public interface IUserService {
    User findUserById(Long user_id);
}
