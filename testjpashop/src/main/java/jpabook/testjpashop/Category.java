package jpabook.testjpashop;

import jakarta.persistence.*;
import jpabook.testjpashop.items.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@JoinTable(joinColumns = @JoinColumn(name = "category_id"), )
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    private List<Item> items = new ArrayList<>();

    @OneToMany
    private Category parent;

    @ManyToOne
    private List<Category> child = new ArrayList<>();
}
