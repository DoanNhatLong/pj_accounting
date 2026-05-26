package com.example.exam_accounting.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "document", indexes = {
        @Index(name = "idx_doc_type_ref", columnList = "document_type, reference_number")
})
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false, columnDefinition = "ENUM('INVOICE', 'RECEIPT', 'PAYMENT', 'WAREHOUSE_IMPORT')")
    DocumentType documentType;

    @Size(max = 50)
    @NotNull
    @Column(name = "reference_number", nullable = false, length = 50)
    private String referenceNumber;

    @NotNull
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Size(max = 255)
    @NotNull
    @Column(name = "partner_name", nullable = false)
    private String partnerName;

    @Size(max = 20)
    @Column(name = "partner_tax_code", length = 20)
    private String partnerTaxCode;

    @NotNull
    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "status", columnDefinition = "ENUM('PENDING', 'PROCESSING', 'PROCESSED', 'ERROR')")
    private String status;

    @Size(max = 512)
    @Column(name = "file_path", length = 512)
    private String filePath;

    @Column(name = "ocr_raw_result", columnDefinition = "json")
    private String ocrRawResult;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public enum DocumentType {
        INVOICE,
        RECEIPT,
        PAYMENT,
        WAREHOUSE_IMPORT
    }
}