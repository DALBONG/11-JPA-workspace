package hellojpa;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
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

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery((Member.class));

            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
            List<Member> resultList = em.createQuery(cq).getResultList();

            for(Member mem : resultList){
                System.out.println("member : " + mem);
            }



            ts.commit();

            // member.setCreatedDate(LocalDateTime.now());
            // 지금의 시간 추출 메소드

             /* 프록시
            // Member findMem = em.find(Member.class, member.getId());
            Member refMem = em.getReference(Member.class, member1.getId());
            System.out.println("m1 : " + refMem.getClass());

            Member findMem = em.find(Member.class, member1.getId());
            System.out.println("ref : " + findMem.getClass());

            System.out.println((refMem == findMem));
            */

            /* 지연 로딩
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("Hoowe");
            member1.setTeam(team);
            em.persist(member1);

            em.flush();
            em.clear();

            Member m = em.find(Member.class, member1.getId());
            System.out.println("m : "+ m.getTeam().getClass());

            List<Member> result = em.createQuery("select m from Member m join fetch m.team", Member.class).getResultList();
            // -> LAZY여도 쿼리 불러오는ㄴ 법

            System.out.println("===========================");
            m.getTeam().getName();//프록시 초기화, LAZY시, 이 때 쿼리를 불러옴.
            System.out.println("===========================");
            */

        }catch (Exception e){
            ts.rollback();
            e.printStackTrace(); // 에러문구 보기.
        }finally {
            // 사용 후 반납 해야 함.
            em.close();
        }
        emf.close();
    }

    /*
    private static void logic(Member m1, Member m2) {
        System.out.println("m1 == m2 : " + (m1 instanceof Member) );
        System.out.println("m1 == m2 : " + (m2 instanceof Member) );
    }
    */

}
