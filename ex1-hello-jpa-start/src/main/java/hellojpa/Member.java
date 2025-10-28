package hellojpa;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
// @Table(uniqueConstraints = "") 유니크 제약조건과 함게 제약조건 명 설정할떄
// @Table(name = "MEMBER_TB") //클래스 명과 테이블 명이 다를 때, naem ="tb명" 을 명시적으로 써줌
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private  Long id;

    @Column(name = "USER_NAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_DD", insertable = false, updatable = false)
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();

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



