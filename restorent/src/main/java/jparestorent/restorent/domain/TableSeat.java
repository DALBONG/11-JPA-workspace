package jparestorent.restorent.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Slf4j
public class TableSeat {

    @Id @GeneratedValue
    @Column(name = "tableSeat_id")
    private Long id;

    private String name;

    private int capacity;

    private TableStatus status;

    @OneToMany(mappedBy = "tableSeat")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "tableSeat")
    private List<Order> orders = new ArrayList<>();

    // ======================= 비즈니스 로직 =============================
    // 예약 생성시 startTime +2시간 후, 자동으로 테이블 상태 예약가능으로 변경.
    public void tableStatus(LocalTime currentTime){
        for (Reservation reservation : reservations) {
            if(reservation.getEndTime().isBefore(currentTime)
                    && this.status == TableStatus.UNAVAILABLE){

                log.info("[테이블 상태 변경] 테이블'{}' 예약 종료 (종료 시간 : {}), 상태 변경 -> AVAILABLE",
                            this.name != null? this.name : this.id,
                            reservation.getEndTime());

                this.status = TableStatus.AVAILABLE;
            }
        }
    }




}
