package com.example.exam_accounting.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Size(max = 20)
    @NotNull
    @Column(name = "tax_code", nullable = false, length = 20)
    private String taxCode;

    @Column(name = "status", columnDefinition = "ENUM('ACTIVE', 'INACTIVE')")
    private String status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "customer")
    private Set<Document> documents = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<UserCustomer> userCustomers = new LinkedHashSet<>();

}