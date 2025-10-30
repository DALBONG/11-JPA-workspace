package helljpa.jpql;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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

            Member member = new Member();
            member.setUsername("userA");
            member.setAge(22);
            em.persist(member);

            Member singleResult = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "userA")
                    .getSingleResult();

            System.out.println(singleResult);


            ts.commit();
        }catch (Exception e){
            ts.rollback();
            e.printStackTrace(); // 에러문구 보기.
        }finally {
            // 사용 후 반납 해야 함.
            em.close();
        }
        emf.close();
    }

}
