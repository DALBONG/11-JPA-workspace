package jpabook.testjpashop.items;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue("A")
@Entity
@Getter @Setter
public class Album {

    private String artist;
    private String etc;
}
