package hellojpa;

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

            Member member1 = new Member();
            member1.setUsername("Auser");

            Member member2 = new Member();
            member2.setUsername("Buser");

            Member member3 = new Member();
            member3.setUsername("Cuser");

            System.out.println("=========================");

            // DB Seq = 1
            // DB seq = 51 / 2
            // DB seq = 101 / 3

            em.persist(member1); // 영속
            em.persist(member2);
            em.persist(member3);

            System.out.println("member 1 :" + member1.getId() );
            System.out.println("member 2 :" + member2.getId() );
            System.out.println("member 3 :" + member3.getId() );

            System.out.println("=========================");

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
