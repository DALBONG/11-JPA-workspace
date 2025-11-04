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
            Team teamA = new Team();
            teamA.setName("팀1");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀2");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

//            em.flush();
//            em.clear();
            String query = "update Member m set m.age = 50";
            int resultCount = em.createQuery(query).executeUpdate();

            em.clear();
            Member findMem = em.find(Member.class, member1.getId());

            System.out.println("member1.age : " + findMem.getAge());



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
