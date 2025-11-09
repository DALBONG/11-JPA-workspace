package jparestorent.restorent.domain;

import jakarta.persistence.*;
import jparestorent.restorent.domain.menu.Menu;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

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

    // 연관관계 편의 메소드
    /*
    public void setOrder(Order order){
        this.order = order;
        order.getOrderMenus().add(this);
    }
     */

    public void setMenu(Menu menu){
        this.menu = menu;
        menu.getOrderMenus().add(this);
    }

    // ======= 주문, 메뉴 생성 메소드 ========
    public static OrderMenu createOrderMenu(Menu menu, int quantity){
        OrderMenu orderMenu = new OrderMenu();
        orderMenu.setMenu(menu);
        orderMenu.setQuantity(quantity);

        return orderMenu;
    }


}


