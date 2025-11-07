package jparestorent.restorent.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class TableSeat {

    @Id @GeneratedValue
    @Column(name = "tableSeat_id")
    private Long id;

    private String name;

    private int capacity;

    private TableStatus status;

    @OneToMany(mappedBy = "tableSeat")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "tableSeat")
    private List<Order> orders = new ArrayList<>();
}
