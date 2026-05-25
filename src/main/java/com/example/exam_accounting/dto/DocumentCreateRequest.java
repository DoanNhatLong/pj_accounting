package com.example.exam_accounting.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DocumentCreateRequest(
        Long customerId,
        String documentType,
        String referenceNumber,
        LocalDate issueDate,
        String partnerName,
        String partnerTaxCode,
        BigDecimal totalAmount,
        String filePath,
        List<DocumentItemRequest> items
) {
}
