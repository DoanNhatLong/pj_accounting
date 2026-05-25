package com.example.exam_accounting.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "document_bounding_box")
public class DocumentBoundingBox {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Size(max = 100)
    @NotNull
    @Column(name = "field_name", nullable = false, length = 100)
    private String fieldName;

    @NotNull
    @Column(name = "x_coord", nullable = false)
    private Double xCoord;

    @NotNull
    @Column(name = "y_coord", nullable = false)
    private Double yCoord;

    @NotNull
    @Column(name = "width", nullable = false)
    private Double width;

    @NotNull
    @Column(name = "height", nullable = false)
    private Double height;

    @ColumnDefault("1")
    @Column(name = "page_number")
    private Integer pageNumber;

}