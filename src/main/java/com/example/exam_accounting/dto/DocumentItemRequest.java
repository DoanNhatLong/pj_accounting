package com.example.exam_accounting.dto;

import java.math.BigDecimal;

public record DocumentItemRequest(
        String itemName,
        Integer quantity,
        BigDecimal unitPrice,
        String vatRate,
        BigDecimal amount
) {
}
