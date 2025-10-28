package jpabook.jpashop.domain;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 요청이 있을 때마다 만듦.
        EntityManager em = emf.createEntityManager();

        // 트랜잭션 만들고,
        EntityTransaction ts = em.getTransaction();

        ts.begin();

        try{

            // 슈더 코드
            Order order = new Order();
            // order.addOrderItem(new OrderItem()); // altEnter로 연관 관계 편의 메소드 생성

            em.persist(order);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);

            em.persist(orderItem);

            ts.commit();
        }catch (Exception e){
            ts.rollback();
        }finally {
            // 사용 후 반납 해야 함.
            em.close();
        }
        emf.close();
    }
}
