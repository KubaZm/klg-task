package pl.klgsolutions.klgtask.objectforrent;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "objects_for_rent")
public class ObjectForRent {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Float price;

    private Float area;

    private String description;
}
