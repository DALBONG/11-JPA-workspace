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
@Table(name = "TB_Location")
@Getter
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Column(name = "region", nullable = false, length = 100)
    private String region;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "address", nullable = false, length = 1500)
    private String address;

    @Column(name = "name", nullable = false, length = 500,  unique = true)
    private String name;

    @Column(name = "latitude", nullable = false, precision = 9, scale = 6, unique = true)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 9, scale = 6, unique = true)
    private BigDecimal longitude;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    // --- Accommodation 연관관계 ---
    @OneToMany(mappedBy = "location")
    private List<Accommodation> accommodations = new ArrayList<>();

}
