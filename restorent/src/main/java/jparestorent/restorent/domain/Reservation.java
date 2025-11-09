package jparestorent.restorent.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jparestorent.restorent.domain.menu.Main;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Slf4j
public class Reservation {

    @Id @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tableSeat_id")
    private TableSeat tableSeat;

    private int headcount;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;
    private LocalTime endTime;

    private ReservationStatus status;

    @OneToMany(mappedBy = "reservation")
    private List<Order> orders = new ArrayList<>();

    public Reservation(LocalDate date, LocalTime startTime) {
        this.date = date;
        this.startTime = startTime;
    }

    protected Reservation(){} // creteReservation 사용!

    // ========= 연관관계 편의 메소드 =========
    public void setCustomer(Customer customer){
        this.customer = customer;
        customer.getReservations().add(this);
    }

    public void setTableSeat(TableSeat tableSeat){
        this.tableSeat = tableSeat;
        tableSeat.getReservations().add(this);
    }

    //======== 예약 생성 메소드 ========
    public static Reservation createReservation(Customer customer,
                                                TableSeat tableSeat,
                                                int headcount,
                                                LocalDate date,
                                                LocalTime startTime){

        LocalTime endTime = startTime.plusHours(2);

        Reservation reservation = new Reservation();

        reservation.setCustomer(customer);
        reservation.setTableSeat(tableSeat);

        reservation.setHeadcount(headcount);
        reservation.setDate(date);
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setStatus(ReservationStatus.AVAILABLE);


        return reservation;
    }

    // ======================= 비즈니스 로직 =============================
    // 당일 예약은 최소 3시간 전 까지 가능.
    public void validateReserTime(LocalDateTime now){
        LocalDateTime reserDT = LocalDateTime.of(this.date, this.startTime);
        //LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(reserDT.minusHours(3))){
            log.warn("[예약 실패] 현시간 : {}, 예약시간 : {}", now, reserDT);
            throw new IllegalArgumentException("예약은 최소 3시간 전까지 가능데스");
        }else {
            log.info("[예약 성공] 예약시간 : {}, 현시간 : {}", reserDT, now);
        }
    }

    // 예약인원은 1명 미만 불가, 테이블 수용 인원보다 예약인원이 많을 수 없음.
    // 테스트 하기위해 date, startTime의 notNull은 잠시 주석처리.
    public void validateHeadcount(){
        if (this.headcount < 0){
            log.error("[예약 실패] 예약인원 : {}", headcount);
            throw new IllegalArgumentException("예약인원은 최소 1명 이상이어야 하데스");
        }
        if (this.tableSeat == null){
            throw new IllegalArgumentException("테이블 정보가 없어요");
        }
        if (this.headcount > this.tableSeat.getCapacity()){
            log.error("[예약 실패] 예약 인원 : {}, 테이블 수용인원 : {}", headcount, tableSeat.getCapacity());
            throw new IllegalArgumentException("예약 인원이 테이블 수용 인원을 초과할 수 없어요!");
        }
        log.info("[예약 성공] 예약 인원 : {}, 테이블 수용 인원 : {}", headcount, tableSeat.getCapacity());
    }

    // 예약시 인원수 <= 메인메뉴 수
    public void validateOrderMenu(OrderMenu... orderMenus){
        if(orderMenus == null || orderMenus.length == 0){
            throw new IllegalArgumentException("예약시 메뉴 주문은 필수데스");
        }

        long mainCount = Arrays.stream(orderMenus)
                // 메뉴 목록을 stream에 올림. -> [OrderMenu(필라프), OrderMenu(스테이크)...]
                .filter(orderMenu -> orderMenu.getMenu() instanceof Main)
                // 실제 Menu객체를 꺼내 Main 클래스의 인스턴스만 뽑음. (Main이 아닌것은 걸러냄)
                .mapToInt(OrderMenu::getQuantity)
                // 뽑힌 OrderMenu의 객체들을 int 타입으로 바꿔(map) 주문수량에 넣음
                .sum(); // 합산 결과를 mainCount에 저장.

        if (mainCount < this.headcount){
            throw new IllegalArgumentException("예약 인원수에 맞게 최소한의 메인 메뉴를 선택해주세요!");
        }
    }
    // 예약 가능 시간은 11:00~22:00, 예약 시도 시간은 19시까지 가능.
    public void validateReser(LocalTime currentTime){
        if(this.startTime == null){
            throw new IllegalArgumentException("예약시간이 설정 되지 않았아요");
        }

        LocalTime openTime = LocalTime.of(11, 0);
        LocalTime lastReserTime = LocalTime.of(22, 0);

        if(this.startTime.isBefore(openTime)){
            log.warn("[예약 실패] 예약시간 {}이 오픈시간 {} 전입니다", this.startTime, openTime);
            throw new IllegalArgumentException("예약은 " + openTime + "부터 가능데스.");
        }
        if(this.startTime.isAfter(lastReserTime)){
            log.warn("[예약 실패] 예약시간 {}이 마감시간 {} 후 입니다", this.startTime, lastReserTime);
            throw new IllegalArgumentException("마지막 예약 가능 시간은 " + lastReserTime + "까지 데스.");
        }

        LocalTime openReser = LocalTime.of(11, 0);
        LocalTime closeReser = LocalTime.of(19, 0);

        if(currentTime.isBefore(openReser) || currentTime.isAfter(closeReser)){
            log.warn("[예약 실패] 현재시간 {}이 예약 신청 가능시간(11~19)이 아닙니다", currentTime);
            throw new IllegalArgumentException("예약은 " + openReser + "부터 " + closeReser + "까지 입니다.");
        }
        log.info("[예약 성공] 예약시간 : {}, 현재 시간 {}", this.startTime, currentTime);
    }
}
