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
public class CartResponseDTO implements ResponseDTO {
    private UUID id;
    private UUID customerId;
    private String status;
    private String couponCode;
    private BigDecimal discountAmount;
    private String discountCurrency;
}
