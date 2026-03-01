package com.example.inventory.presentation.http.request;

import com.example.shared.presentation.http.RequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseStockRequestDTO implements RequestDTO {
    private UUID productId;
    private UUID warehouseId;
    private Integer quantity;
}
