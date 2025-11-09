package jparestorent.restorent.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import jparestorent.restorent.domain.menu.Main;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ReservationTest {

    @Autowired EntityManager em;
    
    @Test
    void setCustomer_연관편의() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setName("달봉이");
        customer.setPhone("010-9011-5641");
        customer.setEmail("dalbong2@naver.com");

        em.persist(customer);

        Reservation reservation = new Reservation();
        //when
        reservation.setCustomer(customer);
    
        //then
        assertEquals(customer, reservation.getCustomer());
        assertEquals(customer.getName(), reservation.getCustomer().getName());
        assertTrue(customer.getReservations().contains(reservation));
    }


    @Test
    void setTableSeat_연관편의() throws Exception {
        //given
        TableSeat tableSeat = new TableSeat();
        tableSeat.setName("달봉이");
        tableSeat.setCapacity(6);
        em.persist(tableSeat);

        Reservation reservation = new Reservation();

        //when
        reservation.setTableSeat(tableSeat);

        //then
        assertEquals(tableSeat, reservation.getTableSeat());
        assertTrue(tableSeat.getReservations().contains(reservation));
    }

    
    @Test
    void 예약생성_테스트() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setName("김달봉");
        customer.setPhone("01090115641");
        customer.setEmail("dalbong2@naver.com");
        em.persist(customer);

        TableSeat tableSeat = new TableSeat();
        tableSeat.setName("T7");
        tableSeat.setCapacity(4);
        em.persist(tableSeat);

        LocalDate date = LocalDate.of(2025,11,11);
        LocalTime startTime = LocalTime.of(19,0);

        //when
        Reservation reservation = Reservation.createReservation(customer, tableSeat, 2, date, startTime);
        em.persist(reservation);

        em.flush();
        em.clear();

        //then
        Reservation findReservation = em.find(Reservation.class, reservation.getId());

    }
    
    @Test
    void 예약실패_테스트() throws Exception {
        //given


        LocalDate today = LocalDate.now();
        LocalTime reserTime = LocalTime.now().plusHours(2);
        Reservation reservation = new Reservation(today, reserTime);

        //when
        assertThrows(IllegalArgumentException.class, ()->{
           reservation.validateReserTime();
        });
    
        //then
        System.out.println("예약실패 테스트 (현시간 기준 3시간 내 예약 불가.)");
    }

    @Test
    void 예약성공_테스트() throws Exception {
        //given
        LocalDate today = LocalDate.now();
        LocalTime reserTime = LocalTime.now().plusHours(4);
        Reservation reservation = new Reservation(today, reserTime);
        //when
        reservation.validateReserTime();
        em.persist(reservation);

        em.flush();
        em.clear();

        //then
        Reservation findReser = em.find(Reservation.class, reservation.getId());

        System.out.println("예약 번호 : " + findReser.getId());
        System.out.println("예약 날짜 : " + findReser.getDate());
        System.out.println("예약 시간 : " + findReser.getStartTime());

        assertNotNull(findReser);
    }

    @Test
    void 예약실패_수용인원초과() throws Exception {
        //given
        TableSeat tableSeat = new TableSeat();
        tableSeat.setName("T3");
        tableSeat.setCapacity(4);
        em.persist(tableSeat);

        Reservation reservation = new Reservation();
        reservation.setHeadcount(6);
        reservation.setTableSeat(tableSeat);
        em.persist(reservation);

        //then
        try{
            reservation.validateHeadcount();
        }catch (Exception e){
            System.out.println("예약실패 테스트 : " + e.getMessage());
            return;
        }
        fail("예외 발생 X");
    }
    
    @Test
    void 예약성공_정상인원() throws Exception {
        //given
        TableSeat tableSeat = new TableSeat();
        tableSeat.setName("T7");
        tableSeat.setCapacity(4);
        em.persist(tableSeat);

        Reservation reservation = new Reservation();
        reservation.setTableSeat(tableSeat);
        reservation.setHeadcount(4);

        //when
        reservation.validateHeadcount();

        em.persist(reservation);

        em.flush();
        em.clear();

        //then
        Reservation findReser = em.find(Reservation.class, reservation.getId());

        System.out.println("예약 테이블 : " + findReser.getTableSeat().getName());
        System.out.println("예약 인원 : " + findReser.getHeadcount());
        System.out.println("수용 인원 : " + findReser.getTableSeat().getCapacity());

        assertNotNull(findReser);
    }

    @Test
    void 예약실패_메인메뉴부족() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setName("달봉이");
        customer.setPhone("01099398342");
        em.persist(customer);

        TableSeat tableSeat = new TableSeat();
        tableSeat.setName("T4");
        tableSeat.setCapacity(4);
        em.persist(tableSeat);

        Reservation reservation = Reservation.createReservation(
                customer, tableSeat, 3, LocalDate.of(2025, 11,11), LocalTime.of(19, 0));

        em.persist(reservation);

        Main main1 = new Main();
        main1.setName("스테이크");
        main1.setPrice(27000);
        em.persist(main1);

        OrderMenu orderMenu = OrderMenu.createOrderMenu(main1, 2);

        //then
        try{
            reservation.validateOrderMenu(orderMenu);
        }catch (Exception e){
            System.out.println("예약 실패 : " + e.getMessage());
            return;
        }
        fail("예외 발생 X");
    }

    @Test
    void 예약성공_메뉴충분() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setName("김달봉");
        customer.setPhone("01039842939");
        em.persist(customer);

        TableSeat tableSeat = new TableSeat();
        tableSeat.setName("T3");
        tableSeat.setCapacity(4);
        em.persist(tableSeat);

        Reservation reservation = Reservation.createReservation(
                customer, tableSeat, 3,
                LocalDate.of(2025,11,11),
                LocalTime.of(19, 0));
        em.persist(reservation);

        Main main1 = new Main();
        main1.setSteak("살치스테이크");
        main1.setPilaf("바나나필라프");
        em.persist(main1);

        OrderMenu orderMenu = OrderMenu.createOrderMenu(main1, 3);

        //when
        reservation.validateOrderMenu(orderMenu);
        em.flush();
        em.clear();

        //then
        em.find(Reservation.class, reservation.getId());
    }

    
}