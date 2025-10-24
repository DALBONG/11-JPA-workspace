package hellojpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
// @Table(name = "MEMBER_TB") //클래스 명과 테이블 명이 다를 때, 이렇게 써줌
public class Member {

    @Id //PK
    private Long id;
    // @Column(name = "userName") // 컬럼명 또한 다른 컬럼명으로 하고싶다면 이렇게
    private String name;

    public Member(){}

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
