package jparestorent.restorent.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import jparestorent.restorent.domain.menu.Main;
import jparestorent.restorent.domain.menu.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class OrderMenuTest {

    @Autowired EntityManager em;

    @Test
    void setOrder_연관관계() throws Exception {
        //given
        Order order = new Order();
        order.setOrderTime(LocalDateTime.now());
        em.persist(order);

        OrderMenu orderMenu = new OrderMenu();
        //when
        orderMenu.setOrder(order);

        //then
        assertEquals(order, orderMenu.getOrder());
        assertTrue(order.getOrderMenus().contains(orderMenu));
    }

    @Test
    void 주문메뉴생성() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setName("김달봉");
        customer.setPhone("01099340934");
        em.persist(customer);

        TableSeat tableSeat = new TableSeat();
        tableSeat.setName("T4");
        em.persist(tableSeat);

        Reservation reservation = Reservation.createReservation(customer, tableSeat, 2,
                                LocalDate.of(2025, 11,11),
                                LocalTime.of(19,0));
        em.persist(reservation);

        Main menu = new Main();
        menu.setName("바나나필라프");
        menu.setPrice(11000);
        em.persist(menu);

        OrderMenu orderMenu = OrderMenu.createOrderMenu(menu, 2);
        //when
        Order order = Order.createOrder(reservation, tableSeat, orderMenu);
        em.persist(order);


        //then
        em.flush();
        em.clear();

        Order findOrder = em.find(Order.class, order.getId());

        assertEquals(menu, orderMenu.getMenu());
        assertEquals(2, orderMenu.getQuantity());
        assertEquals(22000, orderMenu.getMenu().getPrice() * orderMenu.getQuantity());
    }
}