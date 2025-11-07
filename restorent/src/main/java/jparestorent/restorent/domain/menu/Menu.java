package jparestorent.restorent.domain.menu;

import jakarta.persistence.*;
import jparestorent.restorent.domain.OrderMenu;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Menu {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    private String name;
    private int price;

    @OneToMany(mappedBy = "menu")
    private List<OrderMenu> orderMenus = new ArrayList<>();
}
