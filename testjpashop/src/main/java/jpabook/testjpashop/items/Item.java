package jpabook.testjpashop.items;

import jakarta.persistence.*;
import jpabook.testjpashop.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private String stockQuantity;

    private List<Category> categories = new ArrayList<>();
}
