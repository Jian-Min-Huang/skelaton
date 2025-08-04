package com.example.product.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private BigDecimal amount;
    private String currency;

    public static Price fromAmountAndCurrency(final BigDecimal amount, final String currency) {
        return Price
                .builder()
                .amount(amount)
                .currency(currency)
                .build();
    }
}