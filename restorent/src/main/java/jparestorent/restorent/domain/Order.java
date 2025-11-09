package jparestorent.restorent.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tableSeat_id")
    private TableSeat tableSeat;

    private LocalDateTime orderTime;

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    // ======= 연관관계 편의 메소드 =======
    public void setReservation(Reservation reservation){
        this.reservation = reservation;
        reservation.getOrders().add(this);
    }

    public void setTableSeat(TableSeat tableSeat){
        this.tableSeat = tableSeat;
        tableSeat.getOrders().add(this);
    }

    public void addOrderMenu(OrderMenu orderMenu){
        orderMenus.add(orderMenu);
        orderMenu.setOrder(this);
    }

    // ====== 주문 생성 메소드 ======
    public static Order createOrder(Reservation reservation,
                                    TableSeat tableSeat,
                                    OrderMenu... orderMenus){
        Order order = new Order();
        order.setReservation(reservation);
        order.setTableSeat(tableSeat);

        for (OrderMenu orderMenu : orderMenus) {
            order.addOrderMenu(orderMenu);
        }

        order.setOrderTime(LocalDateTime.now());

        return order;
    }

}
