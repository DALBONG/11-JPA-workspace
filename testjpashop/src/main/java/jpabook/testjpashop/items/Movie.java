package jpabook.testjpashop.items;

import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;

@Getter
@DiscriminatorValue("M")
public class Movie {

    private String director;
    private String actor;
}
