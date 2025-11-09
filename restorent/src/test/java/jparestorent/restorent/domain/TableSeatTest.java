package jparestorent.restorent.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TableSeatTest {

    @Test
    void 예약시간종료후_상태변경() throws Exception {
        //given
        TableSeat tableSeat = new TableSeat();
        tableSeat.setStatus(TableStatus.UNAVAILABLE);

        Reservation endReser = new Reservation();
        endReser.setEndTime(LocalTime.of(15,00));

        Reservation futureReser = new Reservation();
        futureReser.setEndTime(LocalTime.of(15, 01));

        tableSeat.getReservations().add(endReser);
        tableSeat.getReservations().add(futureReser);

        System.out.println("Before Status : " + tableSeat.getStatus() );
        //when
        tableSeat.tableStatus(LocalTime.of(15,10));

        //then
        System.out.println("After status : " + tableSeat.getStatus());
        assertEquals(TableStatus.AVAILABLE, tableSeat.getStatus(), "종료된 예약이 있으면 테이블 상태가 AVAILABLE로 변경");
    }
}