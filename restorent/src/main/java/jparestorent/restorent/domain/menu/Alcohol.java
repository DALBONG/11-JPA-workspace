package jparestorent.restorent.domain.menu;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("A")
public class Alcohol {

    private String wine;
    private String beer;
    private String highball;
}
