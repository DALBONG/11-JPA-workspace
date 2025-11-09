package jparestorent.restorent.domain;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class OrderTest {

    @Autowired EntityManager em;
    LocalDate date = LocalDate.now();
    LocalTime startTime = LocalTime.now();
    LocalTime endTime = LocalTime.now().plusHours(2);

    @Test
    void setReservation_연관관계() throws Exception {
        //given
        Reservation reservation = new Reservation();
        reservation.setHeadcount(6);
        reservation.setDate(date);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        em.persist(reservation);

        Order order = new Order();

        //when
        order.setReservation(reservation);

        //then
        assertEquals(reservation, order.getReservation());
        assertTrue(reservation.getOrders().contains(order));
    }
    
    @Test
    void setTableSeat_연관관계() throws Exception {
        //given
        TableSeat tableSeat = new TableSeat();
        tableSeat.setName("달봉");
        tableSeat.setCapacity(4);
        em.persist(tableSeat);

        Order order = new Order();

        //when
        order.setTableSeat(tableSeat);
    
        //then
        assertEquals(tableSeat, order.getTableSeat());
        assertTrue(tableSeat.getOrders().contains(order));
    }
    
}