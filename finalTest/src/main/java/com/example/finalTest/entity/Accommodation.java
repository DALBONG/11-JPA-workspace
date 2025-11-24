package com.example.finalTest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_Accommodation")
@Getter @Setter
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Column(name = "base_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "weekday_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal weekdayPrice;

    @Column(name = "weekend_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal weekendPrice;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "detail_info", nullable = false, length = 4000)
    private String detailInfo;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccomodationType type;

    @Column(name = "tags", nullable = false, length = 30)
    private String tags;

    @Column(name = "status", nullable = false, columnDefinition = "char(1) DEFAULT 'N'")
    private String status = "N";

    // --- Location 연관관계 ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    // --- Reservation 연관관계 ---
    @OneToMany(mappedBy = "accommodation_id")
    private List<Reservation> reservations = new ArrayList<>();

    // --- PricePolicy 연관관계 ---
    @OneToMany(mappedBy = "accommodation_id")
    private List<PricePolicy> pricePolicies = new ArrayList<>();

}
