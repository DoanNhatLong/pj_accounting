package com.example.exam_accounting.dto;

public record DocumentRequestDto(
        Long customerId,
        String documentType,
        String issueDate,
        String partnerName,
        String partnerTaxCode,
        String referenceNumber,
        Double totalAmount
) {
}
