package jpabook.testjpashop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    //이것도
    private String name;

    //이것까지도
    @Embedded
    private Address address;

    // 이것도 테스트고
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
