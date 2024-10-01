package com.udea.modulo_pagos.service.implementation;

import com.udea.modulo_pagos.entities.Payment;
import com.udea.modulo_pagos.entities.User;
import com.udea.modulo_pagos.repositories.IPaymentMethodXUserRepository;
import com.udea.modulo_pagos.repositories.IUserRepository;
import com.udea.modulo_pagos.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public User findUserById(Long user_id) {
        return userRepository.findById(user_id).orElseThrow();
    }

}
