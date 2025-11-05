package jpabook.jpashop2.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop2.Exception.NotEnoughStockException;
import jpabook.jpashop2.domain.Address;
import jpabook.jpashop2.domain.Member;
import jpabook.jpashop2.domain.Order;
import jpabook.jpashop2.domain.OrderStatus;
import jpabook.jpashop2.domain.item.Book;
import jpabook.jpashop2.domain.item.Item;
import jpabook.jpashop2.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook("사골 JPA", 10000, 12);

        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태 Order");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 함");
        assertEquals(10, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 함");
        // 기대값,
    }

    // con alt p : 파라미터 추출
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원A");
        member.setAddress(new Address("서울", "골목로", "58483"));
        em.persist(member);
        return member;
    }


    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("사골JPA", 10000, 12);

        int orderCount = 13;

        //when
//        try{
//            Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
//        }catch (NotEnoughStockException e){
//            return;
//        }
        assertThrows(NotEnoughStockException.class,
                ()-> orderService.order(member.getId(), item.getId(), orderCount));

        //then
        //fail("재고 수량 부족 예외 발생해야 함");

    }

    
    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("사골 JPA", 10000, 12);

        int orderCount = 2;
        
        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.CANCLE, getOrder.getStatus(), "주문취소시 상태는 CANCEL로");
        assertEquals(12, item.getStockQuantity(), "주문 취소 상품은 재고가 그만큼 증가해야 함");
    }
    
}