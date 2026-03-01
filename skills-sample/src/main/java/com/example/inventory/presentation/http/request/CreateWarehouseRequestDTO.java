package com.example.inventory.presentation.http.request;

import com.example.shared.presentation.http.RequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWarehouseRequestDTO implements RequestDTO {
    private String name;
    private String code;
    private String city;
    private String district;
    private String street;
    private String zipCode;
    private Integer capacity;
}
