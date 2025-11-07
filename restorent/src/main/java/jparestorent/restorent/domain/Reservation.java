package jparestorent.restorent.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Reservation {

    @Id @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tableSeat_id")
    private TableSeat tableSeat;

    private int headcount;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private ReservationStatus status;

    @OneToMany(mappedBy = "reservation")
    private List<Order> orders = new ArrayList<>();


    // ========= 연관관계 편의 메소드 =========



}
