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

            Order order = em.find(Order.class, 1L);
            Long memberId = order.getMemberId();

            Member member = em.find(Member.class, memberId);

            Member orderMember = order.getMember();


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
