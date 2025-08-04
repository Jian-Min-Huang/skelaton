package com.example.order.application.adapter.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressVoModel {
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}