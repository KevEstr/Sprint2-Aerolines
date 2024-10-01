package com.udea.modulo_pagos.service.implementation;

import com.udea.modulo_pagos.entities.Booking;
import com.udea.modulo_pagos.entities.PaymentMethod;
import com.udea.modulo_pagos.entities.PaymentMethodXUser;
import com.udea.modulo_pagos.entities.User;
import com.udea.modulo_pagos.graphql.InputPaymentMethodXUser;
import com.udea.modulo_pagos.repositories.IPaymentMethodXUserRepository;
import com.udea.modulo_pagos.repositories.IUserRepository;
import com.udea.modulo_pagos.service.IPaymentMethodService;
import com.udea.modulo_pagos.service.IPaymentMethodXUserService;
import com.udea.modulo_pagos.service.IUserService;
import com.udea.modulo_pagos.utils.CreditCardEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.List;
import java.util.Optional;

import static com.udea.modulo_pagos.utils.CreditCardEncryption.*;
import static com.udea.modulo_pagos.utils.CreditCardValidator.*;

@Service
public class PaymentMethodXUserServiceImpl implements IPaymentMethodXUserService {

    @Autowired
    private IPaymentMethodXUserRepository paymentMethodXUserRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPaymentMethodService paymentMethodService;

    @Autowired
    private IUserService userService;

    public PaymentMethodXUser createPaymentMethodXUser(InputPaymentMethodXUser inputPaymentMethodXUser) throws Exception {
        PaymentMethodXUser paymentMethodXUser = new PaymentMethodXUser();
        User user = userService.findUserById(inputPaymentMethodXUser.getUser_id());
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(inputPaymentMethodXUser.getPayment_method_id());
        String card_number=inputPaymentMethodXUser.getCard_number();
        String newCardLast4Digits = card_number.substring(card_number.length() - 4);

        if(!isValidCard(inputPaymentMethodXUser.getCard_number())){
            throw new Exception("Invalid card number.");
        }

        if(!isValidExpiryDate(inputPaymentMethodXUser.getExpiry_date())){
            throw new Exception("Invalid expiry date.");
        }
        //revisa si el ccv coincide con el tipo de ccv del emisor de la tarjeta
        if(!isValidCCV(inputPaymentMethodXUser.getCcv(), inputPaymentMethodXUser.getCard_type())){
            throw new Exception("Invalid CCV.");
        }

        //revisa si el comienzo del numero de la tarjeta coincide con el emisor de la tarjeta
        if(!isCardTypeValid(inputPaymentMethodXUser.getCard_number(), inputPaymentMethodXUser.getCard_type())){
            throw new Exception("The card type does not match the provided number.");
        }

        // revisa si los ultimos 4 digitos de alguna de de las tarjetas guardadas es igual a la nueva
        List<PaymentMethodXUser> encryptedPaymentMethods = paymentMethodXUserRepository.findByUserId(user.getId());
        for (PaymentMethodXUser payment_Method : encryptedPaymentMethods) {
            if (payment_Method.getLast4Digits().equals(newCardLast4Digits)) {
                throw new Exception("This card number is too similar to an existing payment method.");
            }
        }

        SecretKey secretKey = generateSecretKey();
        byte[] iv = generateIv();

        // Cifrar los datos
        String encryptedCreditCard = encrypt(inputPaymentMethodXUser.getCard_number(), secretKey, iv);
        String encryptedCvv = encrypt(inputPaymentMethodXUser.getCcv(), secretKey, iv);
        String encryptedExpirationDate = encrypt(inputPaymentMethodXUser.getExpiry_date(), secretKey, iv);
        String ecryptedType = encrypt(inputPaymentMethodXUser.getCard_type(), secretKey, iv);

        //seteo y guardado
        paymentMethodXUser.setCard_number(encryptedCreditCard);
        paymentMethodXUser.setCcv(encryptedCvv);
        paymentMethodXUser.setExpiry_date(encryptedExpirationDate);
        paymentMethodXUser.setCard_type(ecryptedType);
        paymentMethodXUser.setUser(user);
        paymentMethodXUser.setPayment_method(paymentMethod);
        paymentMethodXUser.setLast4Digits(newCardLast4Digits);

        paymentMethodXUserRepository.save(paymentMethodXUser);


        return paymentMethodXUser;
    }

    public List<PaymentMethodXUser> findAllByUserId(Long userId) {
        return paymentMethodXUserRepository.findByUserId(userId);
    }

    public void deletePaymentMethodXUser(Long paymentMethodXUserId) throws Exception {
        Optional<PaymentMethodXUser> paymentMethodXUser = paymentMethodXUserRepository.findById(paymentMethodXUserId);
        if (paymentMethodXUser.isPresent()) {
            paymentMethodXUserRepository.deleteById(paymentMethodXUserId);
        } else {
            throw new Exception("Payment method not found.");
        }
    }

}
