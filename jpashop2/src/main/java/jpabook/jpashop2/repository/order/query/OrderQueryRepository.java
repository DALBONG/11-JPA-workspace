package jpabook.jpashop2.repository.order.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos(){
        List<OrderQueryDto> result = findOrders(); // 5개

        result.forEach(o -> {
            List<OrderItemQueryDto> orderitems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderitems); // 마지막 1개
        });
        return result;
    };

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new jpabook.jpashop2.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                        "from OrderItem oi " +
                        "join oi.item i " +
                        "where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    // 문법상 1:다 관계인 orderItems는 가져올 수 없음. ↑↑ 에서 별도 처리
    private List<OrderQueryDto> findOrders(){
        return em.createQuery(
                        "select new jpabook.jpashop2.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o " +
                                "join o.member m " +
                                "join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }


}
