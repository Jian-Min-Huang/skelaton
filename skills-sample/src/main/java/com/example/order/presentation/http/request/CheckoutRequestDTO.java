package com.example.order.presentation.http.request;

import com.example.shared.presentation.http.RequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequestDTO implements RequestDTO {
    private String orderNumber;
    private String recipientName;
    private String phone;
    private String city;
    private String district;
    private String street;
    private String zipCode;
    private String paymentMethod;
}
