package jpabook.jpashop.domain;

import jakarta.persistence.*;

import javax.lang.model.element.NestingKind;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Category extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "CATEGORY_ITEM",
               joinColumns = @JoinColumn(name = "CATEGORY_ID"),
               inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent; // 셀프조인 느낌

    @OneToMany (mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
}
