package hellojpa;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
// @Table(uniqueConstraints = "") 유니크 제약조건과 함게 제약조건 명 설정할떄
// @Table(name = "MEMBER_TB") //클래스 명과 테이블 명이 다를 때, naem ="tb명" 을 명시적으로 써줌
public class Member extends BaseEntity {
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private  Long id;

    @Column(name = "USER_NAME")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)// 프록시로 조회
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToOne(fetch = FetchType.LAZY)
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



