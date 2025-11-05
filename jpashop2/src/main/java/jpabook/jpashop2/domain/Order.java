package jpabook.jpashop2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();


    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCLE]

    protected Order(){}; // createOrder를 쓰시오!

    //======= 연간관계 편의 메소드 ========
    public void setMember(Member member){
        this.member = member; // 위의 member,
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // ==== 주문 생성 메소드 ====
            // 누가 member, 무엇을 샀는지orderitem?, 배송은 delivery?
    /*
        가변 인자 문법 OrderItem... orderItems
                => 컴파일 될 때 OrderItem[] orderItems 배열형으로 됨.
            createOrder(m, d, orderItem1)
            createOrder(m, d, orderItem1, orderItem2)
          만약 배열로 했을 경우? 위 처럼 안됨.
          createOrder(m, d, orderItem1[0])
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member); // 구매자, 연관관계 편의 메소드
        order.setDelivery(delivery); // 배송 정보, 연관관계 편의 메소드

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem); // 구매 물품, 연관관계 편의 메소드
        }
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);
        return order;
    }

    // ==== 비즈니스 로직 ====
    /**
     주문 취소
     */
    public void cancel(){
        if (delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("배송 완료된 상품은 취소가 불가능 함.");
        }
        this.setStatus(OrderStatus.CANCLE);
        // 취소 되면 재고 원복
        for (OrderItem orderItem : orderItems) { // 취소시 재고수량 원복 로직.
            orderItem.cancel();
        }
    }

    /**
     * 주문 조회
     */
    // 전체 주문 가격 조회
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }


}
