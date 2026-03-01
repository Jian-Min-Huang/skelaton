package com.example.order.presentation.http.response;

import com.example.shared.presentation.http.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO implements ResponseDTO {
    private UUID id;
    private String orderNumber;
    private UUID customerId;
    private String recipientName;
    private String phone;
    private String city;
    private String district;
    private String street;
    private String zipCode;
    private BigDecimal totalAmount;
    private String totalCurrency;
    private String status;
    private String paymentMethod;
}
