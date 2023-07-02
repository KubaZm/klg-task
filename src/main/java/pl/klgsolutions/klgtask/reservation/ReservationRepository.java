package pl.klgsolutions.klgtask.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByObjectId(Long id);

    List<Reservation> findAllByObjectNameIgnoreCase(String name);

    List<Reservation> findAllByRenterNameIgnoreCase(String name);

    List<Reservation> findAllByLandlordNameIgnoreCase(String name);
}
