package jparestorent.restorent.domain;

import jakarta.persistence.*;
import jparestorent.restorent.domain.menu.Menu;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class OrderMenu {

    @Id @GeneratedValue
    @Column(name = "order_menu_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int quantity;
}
