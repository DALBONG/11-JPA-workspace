package jparestorent.restorent.domain.menu;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("S")
public class Side {

    private String bread;
    private String salad;
    private String splater;

}
