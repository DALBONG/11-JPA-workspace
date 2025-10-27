package hellojpa;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@SequenceGenerator(
        name = "MEM_SEQ_GENERATOR",
        sequenceName = "seq_mem", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50)
// @Table(uniqueConstraints = "") 유니크 제약조건과 함게 제약조건 명 설정할떄
// @Table(name = "MEMBER_TB") //클래스 명과 테이블 명이 다를 때, naem ="tb명" 을 명시적으로 써줌
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "MEM_SEQ_GENERATOR")
    private  Long id;

    @Column(name = "name", nullable = false)
    private String username;

    public Member () {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}



