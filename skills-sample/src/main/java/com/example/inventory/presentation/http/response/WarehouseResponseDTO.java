package com.example.inventory.presentation.http.response;

import com.example.shared.presentation.http.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponseDTO implements ResponseDTO {
    private UUID id;
    private String name;
    private String code;
    private String city;
    private String district;
    private String street;
    private String zipCode;
    private Integer capacity;
    private String status;
}
