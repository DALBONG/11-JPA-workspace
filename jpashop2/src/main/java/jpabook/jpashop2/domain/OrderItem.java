package jpabook.jpashop2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpabook.jpashop2.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name= "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice; // 주문 가격

    private int count; // 주문 수량

    protected OrderItem(){}; // createOrderItem을 쓰시요! 

    // === 생성 메소드 ===
    // 상품, 얼마, 몇개?
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        // 재고 줄이기.
        item.removeStock(count);
        return orderItem;
    }

    // Order에서 만든 비즈니스 로직
    public void cancel() {
        // 아이템, 재고
        getItem().addStock(count);
    }

    // Order에서 만든 비즈니스 로직
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }


}
