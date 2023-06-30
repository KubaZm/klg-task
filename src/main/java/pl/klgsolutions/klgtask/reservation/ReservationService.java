package pl.klgsolutions.klgtask.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.klgsolutions.klgtask.objectforrent.ObjectForRent;
import pl.klgsolutions.klgtask.objectforrent.ObjectForRentService;
import pl.klgsolutions.klgtask.person.Person;
import pl.klgsolutions.klgtask.person.PersonService;
import pl.klgsolutions.klgtask.reservation.exceptions.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ObjectForRentService objectForRentService;

    @Autowired
    private PersonService personService;

    public List<Reservation> getReservationsByRenter(String name) throws PersonDoesntExistException {
        if (personService.checkIfPersonExistsByName(name))
            return reservationRepository.findAllByRenterNameIgnoreCase(name);
        else
            throw new PersonDoesntExistException();

    }

    public List<Reservation> getReservationsByObject(String name) throws ObjectDoesntExistException {
        if (objectForRentService.checkIfObjectExistsByName(name))
            return reservationRepository.findAllByObjectNameIgnoreCase(name);
        else
            throw new ObjectDoesntExistException();
    }

    public Reservation createReservation(Reservation reservation) throws NoNameProvidedException, PersonDoesntExistException, ObjectDoesntExistException {
        validateCreateReservationRequest(reservation);
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Reservation reservation) throws NoNameProvidedException, PersonDoesntExistException, ObjectDoesntExistException, NoIdProvidedException {
        validateUpdateReservationRequest(reservation);
        return reservationRepository.save(reservation);
    }


    private void validateCreateReservationRequest(Reservation reservation) throws NoNameProvidedException, ObjectDoesntExistException, PersonDoesntExistException {
        reservation.setId(null);

        validateNames(reservation);

        reservationRepository.findAllByObjectId(reservation.getObject().getId()).forEach(r -> {
            if ((reservation.getStartDate().after(r.getStartDate()) && reservation.getStartDate().before(r.getEndDate())) ||
                    (reservation.getEndDate().after(r.getStartDate()) && reservation.getEndDate().after(r.getEndDate())))
                throw new ObjectNotAvailableException();

        });
    }

    private void validateUpdateReservationRequest(Reservation reservation) throws NoNameProvidedException, ObjectDoesntExistException, PersonDoesntExistException, NoIdProvidedException {
        if (reservation.getId() == null)
            throw new NoIdProvidedException();

        validateNames(reservation);

        reservationRepository.findAllByObjectId(reservation.getObject().getId()).forEach(r -> {
            if (!Objects.equals(reservation.getId(), r.getId())) {
                if ((reservation.getStartDate().after(r.getStartDate()) && reservation.getStartDate().before(r.getEndDate())) ||
                        (reservation.getEndDate().after(r.getStartDate()) && reservation.getEndDate().after(r.getEndDate())))
                    throw new ObjectNotAvailableException();
            }
        });
    }

    private void validateNames(Reservation reservation) throws PersonDoesntExistException, NoNameProvidedException, ObjectDoesntExistException {
        if (reservation.getObject().getName() == null)
            throw new NoNameProvidedException("Please provide object's name.");
        else {
            Optional<ObjectForRent> object = objectForRentService.getObjectByName(reservation.getObject().getName());
            if (object.isPresent())
                reservation.setObject(object.get());
            else
                throw new ObjectDoesntExistException();
        }

        if (reservation.getLandlord().getName() == null)
            throw new NoNameProvidedException("Please provide landlord's name.");
        else {
            Optional<Person> landlord = personService.getPersonByName(reservation.getLandlord().getName());
            if (landlord.isPresent())
                reservation.setLandlord(landlord.get());
            else
                throw new PersonDoesntExistException();
        }

        if (reservation.getRenter().getName() == null)
            throw new NoNameProvidedException("Please provide renter's name.");
        else {
            Optional<Person> renter = personService.getPersonByName(reservation.getRenter().getName());
            if (renter.isPresent())
                reservation.setRenter(renter.get());
            else
                throw new PersonDoesntExistException();
        }

    }
}
