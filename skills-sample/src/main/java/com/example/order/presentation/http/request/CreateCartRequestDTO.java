package com.example.order.presentation.http.request;

import com.example.shared.presentation.http.RequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartRequestDTO implements RequestDTO {
    private UUID customerId;
}
