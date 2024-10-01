package com.udea.modulo_pagos.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CreditCardEncryption {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    // Método para generar una clave secreta AES
    public static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(256); // Puedes usar 128, 192 o 256 bits
        return keyGen.generateKey();
    }

    // Método para generar IV (vector de inicialización)
    public static byte[] generateIv() {
        byte[] iv = new byte[16];
        new java.security.SecureRandom().nextBytes(iv);
        return iv;
    }

    // Método para cifrar datos (convertido a Base64)
    public static String encrypt(String data, SecretKey secretKey, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);

        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        // Convertir a Base64 para almacenar en la base de datos
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // Método para descifrar datos (de Base64 a texto claro)
    public static String decrypt(String base64EncryptedData, SecretKey secretKey, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);

        // Convertir de Base64 a bytes para descifrar
        byte[] encryptedData = Base64.getDecoder().decode(base64EncryptedData);
        byte[] decryptedData = cipher.doFinal(encryptedData);

        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    // Ejemplo de uso para cifrar y descifrar una tarjeta de crédito
    public static void main(String[] args) throws Exception {
        String creditCardNumber = "1234-5678-9012-3456";
        String cvv = "123";
        String expirationDate = "12/25";

        // Generar clave secreta y vector de inicialización
        SecretKey secretKey = generateSecretKey();
        byte[] iv = generateIv();

        // Cifrar los datos
        String encryptedCreditCard = encrypt(creditCardNumber, secretKey, iv);
        String encryptedCvv = encrypt(cvv, secretKey, iv);
        String encryptedExpirationDate = encrypt(expirationDate, secretKey, iv);

        // Guardar los datos cifrados en la base de datos (Base64)
        System.out.println("Tarjeta de crédito cifrada (Base64): " + encryptedCreditCard);
        System.out.println("CVV cifrado (Base64): " + encryptedCvv);
        System.out.println("Fecha de expiración cifrada (Base64): " + encryptedExpirationDate);

        // Para descifrar, leer los datos cifrados desde la base de datos
        String decryptedCreditCard = decrypt(encryptedCreditCard, secretKey, iv);
        String decryptedCvv = decrypt(encryptedCvv, secretKey, iv);
        String decryptedExpirationDate = decrypt(encryptedExpirationDate, secretKey, iv);

        // Mostrar los datos descifrados
        System.out.println("Tarjeta de crédito descifrada: " + decryptedCreditCard);
        System.out.println("CVV descifrado: " + decryptedCvv);
        System.out.println("Fecha de expiración descifrada: " + decryptedExpirationDate);
    }
}
