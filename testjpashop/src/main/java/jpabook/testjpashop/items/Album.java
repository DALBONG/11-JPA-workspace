package jpabook.testjpashop.items;

import jakarta.persistence.DiscriminatorValue;
import lombok.Getter;

@Getter
@DiscriminatorValue("A")
public class Album {

    private String artist;
    private String etc;
}
