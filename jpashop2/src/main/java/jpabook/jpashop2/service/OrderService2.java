package jpabook.jpashop2.service;

import jpabook.jpashop2.domain.*;
import jpabook.jpashop2.domain.item.Item;
import jpabook.jpashop2.repository.ItemRepository2;
import jpabook.jpashop2.repository.MemberRepository2;
import jpabook.jpashop2.repository.OrderRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService2 {

    private final OrderRepository2 orderRepository2;
    private final MemberRepository2 memberRepository2;
    private final ItemRepository2 itemRepository2;

    @Transactional
    public Long order(Long memberId, Long itemId, int count){

        // 엔티티 조회
        Member member = memberRepository2.findOne(memberId);
        Item item = itemRepository2.findOne(itemId);
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository2.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    public void cancelOrder(Long orderId){
        // 주문 엔티티 조회
        Order order = orderRepository2.findOne(orderId);
        order.cancel(); // 주문취소 코드
    }

}
