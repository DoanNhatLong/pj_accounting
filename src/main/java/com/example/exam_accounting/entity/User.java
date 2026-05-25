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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 255)
    @Column(name = "password_hash")
    private String passwordHash;

    @Size(max = 100)
    @NotNull
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Size(max = 255)
    @Column(name = "google_id")
    private String googleId;

    @Column(name = "role", columnDefinition = "ENUM('ADMIN', 'USER')")
    private String role;

    @Column(name = "status", columnDefinition = "ENUM('ACTIVE', 'INACTIVE')")
    private String status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "user")
    private Set<AuditLog> auditLogs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Document> documents = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserCustomer> userCustomers = new LinkedHashSet<>();

}