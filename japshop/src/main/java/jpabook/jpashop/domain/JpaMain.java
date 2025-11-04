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

            Book book = new Book();
            book.setName("JPA의 정석");
            book.setAuthor("정봉열");

            em.persist(book);

            em.flush();
            em.clear();

            String query = "select i from Item i where type(i) = Book ";
            List<Item> resultList = em.createQuery(query, Item.class).getResultList();

            for (Item item : resultList) {
                System.out.println("book : " + item.getName() );
            }

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
