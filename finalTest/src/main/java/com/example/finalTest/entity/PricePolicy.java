package com.example.finalTest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PricePolicy")
@Getter
@Setter
public class PricePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 1000)
    private String name;

    @Column(name = "description", nullable = false, length = 4000)
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private PricePolicyType dayOfWeek;

    @Column(name = "min_nights")
    private int minNights;

    @Column(name = "max_nights")
    private int maxNights;

    @Column(name = "min_guests")
    private int minGuests;

    @Column(name = "max_guests")
    private int maxGuests;

    @Column(name = "adjustment_value", precision = 15, scale = 2)
    private BigDecimal adjustmentValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "adjustment_type", nullable = false)
    private AdjustmentType adjustmentType;

    @Column(name = "is_active", nullable = false, columnDefinition = "char(1) DEFAULT 'N'")
    private String isActive = "N";

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    // --- Accommodation 연관관계 ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

}
