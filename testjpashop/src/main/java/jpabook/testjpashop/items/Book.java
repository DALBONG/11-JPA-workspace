package jpabook.testjpashop.items;

import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;

@Getter
@DiscriminatorValue("B")
public class Book {

    private String author;
    private String isbn;
}
