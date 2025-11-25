package jpabook.jpashop2;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop2.domain.*;
import jpabook.jpashop2.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbinit1();
        initService.dbinit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbinit1(){
            System.out.println("=== 데이터 생성 ===");
            Member member = createMember("달봉이", "서울", "무지개로", "10040");
            em.persist(member);

            Book book1 = createBook("JPA1 Book", 10000, 50);
            em.persist(book1);

            Book book2 = createBook("JPA2 Book", 12000, 47);
            em.persist(book2);

            // Order에 cascade=ALL 전략으로 persist 안해도 됨.
            OrderItem order1 = OrderItem.createOrderItem(book1, 10000, 2);
            OrderItem order2 = OrderItem.createOrderItem(book2, 12000, 4);

            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member, delivery, order1, order2);
            em.persist(order);

        }

        public void dbinit2(){
            System.out.println("=== 데이터 생성 2 ===");
            Member member = createMember("나리", "서울", "꽃길로", "42134");
            em.persist(member);

            Book book1 = createBook("Spring1 Book", 13000, 80);
            em.persist(book1);

            Book book2 = createBook("Spring2 Book", 15000, 63);
            em.persist(book2);

            // Order에 cascade=ALL 전략으로 persist 안해도 됨.
            OrderItem order1 = OrderItem.createOrderItem(book1, 13000, 3);
            OrderItem order2 = OrderItem.createOrderItem(book2, 15000, 4);

            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member, delivery, order1, order2);
            em.persist(order);
        }

        private static Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        // 책 객체 생성 메소드.
        private Book createBook(String name, int price, int stockQuantity){
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        // 배송정보 생성 메소드
        private Delivery createDelivery(Member member){
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        };

    }
}
