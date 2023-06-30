package pl.klgsolutions.klgtask.reservation;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import pl.klgsolutions.klgtask.objectforrent.ObjectForRent;
import pl.klgsolutions.klgtask.person.Person;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name="object_id", nullable=false)
    private ObjectForRent object;

    @ManyToOne
    @NotNull
    @JoinColumn(name="landlord_id", nullable=false)
    private Person landlord;

    @ManyToOne
    @NotNull
    @JoinColumn(name="renter_id", nullable=false)
    private Person renter;

    @NotNull
    @Column(name = "start_date")
    private Date startDate;

    @NotNull
    @Column(name = "end_date")
    private Date endDate;

    @NotNull
    private Float cost;
}
