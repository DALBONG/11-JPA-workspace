package jparestorent.restorent.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Customer {

    @Id @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 12)
    private int phone;

    @Column(length = 30)
    private String email;

    @OneToMany(mappedBy = "customer")
    private List<Reservation> reservations = new ArrayList<>();


}
