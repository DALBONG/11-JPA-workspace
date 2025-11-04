package jpabook.jpashop2.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop2.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;

    private int price;
    private int stockQuantity; // 재고수량

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();







}
