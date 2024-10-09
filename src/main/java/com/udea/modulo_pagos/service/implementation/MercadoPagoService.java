package com.udea.modulo_pagos.service.implementation;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MercadoPagoService {

    @Value("${MERCADO_PAGO_ACCESS_TOKEN}")
    private String accessToken;

    public String createPaymentPreference(Long transactionId, Long amount) {
        try {
            // Configurar el token de acceso para Mercado Pago
            MercadoPagoConfig.setAccessToken(accessToken);

            // Crear un cliente de preferencias
            PreferenceClient client = new PreferenceClient();

            // Crear el ítem de la preferencia
            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title("Transaction ID: " + transactionId)
                    .quantity(1)
                    .unitPrice(BigDecimal.valueOf(amount.floatValue())) // Precio del ítem
                    .build();

            // Crear las URLs de retorno
            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:8081/success")
                    .failure("http://localhost:8081/failure")
                    .pending("http://localhost:8081/pending")
                    .build();

            // Agregar los metadatos (incluyendo el transactionId)
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("transactionId", transactionId.toString());
            System.out.println("Metadatos agregados a la preferencia: " + metadata);

            // Crear la solicitud de preferencia con los metadatos
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(List.of(item))
                    .backUrls(backUrls)
                    .metadata(metadata)  // Incluye los metadatos aquí
                    .build();

            // Crear la preferencia usando el cliente
            Preference preference = client.create(preferenceRequest);

            // Retornar el link de inicio de pago
            return preference.getInitPoint();

        } catch (MPException e) {
            throw new RuntimeException("Error al crear la preferencia de Mercado Pago", e);
        } catch (MPApiException e) {
            throw new RuntimeException(e);
        }
    }

}
