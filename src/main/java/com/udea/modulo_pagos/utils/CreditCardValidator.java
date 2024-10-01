package com.udea.modulo_pagos.utils;

import java.time.YearMonth;
import java.util.regex.Pattern;

public class CreditCardValidator {

    public static String getCardType(String cardNumber) {
        String sanitizedCardNumber = cardNumber.replaceAll("\\s", "");

        if (sanitizedCardNumber.startsWith("4") &&
                (sanitizedCardNumber.length() == 13 || sanitizedCardNumber.length() == 16)) {
            return "Visa";
        } else if (sanitizedCardNumber.matches("^(5[1-5][0-9]{14})$")) {
            return "MasterCard";
        } else if (sanitizedCardNumber.startsWith("34") || sanitizedCardNumber.startsWith("37")) {
            return "American Express";
        } else if (sanitizedCardNumber.startsWith("6011") ||
                sanitizedCardNumber.startsWith("65") ||
                (sanitizedCardNumber.length() == 16 && sanitizedCardNumber.matches("^(622[1-9][0-9]{12}|622[1-9][0-9]{10})$"))) {
            return "Discover";
        }
        return "Unknown";
    }

    public static boolean isCardTypeValid(String cardNumber, String cardType) {
        String detectedType = getCardType(cardNumber);
        return detectedType.equalsIgnoreCase(cardType);
    }

    public static boolean isValidCard(String cardNumber) {
        int totalSum = 0;
        boolean isSecondDigit = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            if (isSecondDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit = digit - 9;
                }
            }

            totalSum += digit;
            isSecondDigit = !isSecondDigit;
        }

        return (totalSum % 10 == 0);
    }

    public static boolean isValidCCV(String ccv, String cardType) {
        // Verificar si el CCV tiene 3 o 4 dígitos, dependiendo del tipo de tarjeta
        if (cardType.equalsIgnoreCase("American Express")) {
            return ccv.matches("\\d{4}"); // American Express tiene un CVV de 4 dígitos
        } else {
            return ccv.matches("\\d{3}"); // Visa, MasterCard y otras usan un CVV de 3 dígitos
        }
    }

    public static boolean isValidExpiryDate(String expiryDate) {
        // Verificar si la fecha tiene el formato MM/YY usando una expresión regular
        if (!expiryDate.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            return false;
        }

        String[] parts = expiryDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt("20" + parts[1]); // Añadir el "20" para convertir "YY" a "20YY"

        // Obtener la fecha actual
        YearMonth currentYearMonth = YearMonth.now();
        YearMonth cardExpiry = YearMonth.of(year, month);

        // Verificar que la fecha de expiración sea en el futuro
        return cardExpiry.isAfter(currentYearMonth);
    }

    public static void main(String[] args) {
        String cardNumber = "4539578763621486";  // Número de tarjeta de prueba (Visa)
        String ccv = "123";                      // Ejemplo de CCV
        String expiryDate = "12/25";             // Fecha de vencimiento

        // Validar el número de tarjeta
        if (isValidCard(cardNumber)) {
            System.out.println("El número de tarjeta es válido.");
        } else {
            System.out.println("El número de tarjeta es inválido.");
        }

        // Validar el CCV
        if (isValidCCV(ccv, "Visa")) {
            System.out.println("El CCV es válido.");
        } else {
            System.out.println("El CCV es inválido.");
        }

        // Validar la fecha de vencimiento
        if (isValidExpiryDate(expiryDate)) {
            System.out.println("La fecha de vencimiento es válida.");
        } else {
            System.out.println("La fecha de vencimiento es inválida.");
        }
    }
}
