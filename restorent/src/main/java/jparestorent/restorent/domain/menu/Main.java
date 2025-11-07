package jparestorent.restorent.domain.menu;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue("M")
public class Main {

    private String pasta;
    private String pilaf;
    private String steak;
}
