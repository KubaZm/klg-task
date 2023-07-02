package pl.klgsolutions.klgtask.reservation;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.klgsolutions.klgtask.objectforrent.ObjectForRent;
import pl.klgsolutions.klgtask.objectforrent.ObjectForRentService;
import pl.klgsolutions.klgtask.person.Person;
import pl.klgsolutions.klgtask.person.PersonService;
import pl.klgsolutions.klgtask.reservation.dto.CreateReservationRequest;
import pl.klgsolutions.klgtask.reservation.dto.ReservationDto;
import pl.klgsolutions.klgtask.reservation.dto.ReservationMapper;
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

    private final ReservationMapper mapper = Mappers.getMapper(ReservationMapper.class);

    public List<ReservationDto> getReservationsByRenter(String name) throws PersonDoesntExistException {
        if (personService.checkIfPersonExistsByName(name))
            return mapper.reservationListToDtoList(reservationRepository.findAllByRenterNameIgnoreCase(name));
        else
            throw new PersonDoesntExistException();

    }

    public List<ReservationDto> getReservationsByObject(String name) throws ObjectDoesntExistException {
        if (objectForRentService.checkIfObjectExistsByName(name))
            return mapper.reservationListToDtoList(reservationRepository.findAllByObjectNameIgnoreCase(name));
        else
            throw new ObjectDoesntExistException();
    }

    public ReservationDto createReservation(CreateReservationRequest request) throws NoNameProvidedException, PersonDoesntExistException, ObjectDoesntExistException {
        Reservation reservation = mapper.createReservationRequestToReservation(request);
        validateNames(reservation);
        validateObjectAvailability(reservation, false);
        return mapper.reservationToDto(reservationRepository.save(reservation));
    }

    public ReservationDto updateReservation(ReservationDto reservationDto) throws NoNameProvidedException, PersonDoesntExistException, ObjectDoesntExistException {
        Reservation reservation = mapper.dtoToReservation(reservationDto);
        validateNames(reservation);
        validateObjectAvailability(reservation, true);
        return mapper.reservationToDto(reservationRepository.save(reservation));
    }

    private void validateObjectAvailability(Reservation reservation, boolean isUpdateRequest) {

        reservationRepository.findAllByObjectId(reservation.getObject().getId()).forEach(r -> {
            if (!isUpdateRequest || !Objects.equals(reservation.getId(), r.getId())) { // for update requests we want to skip checking availability of the object in the very reservation we want to change
                if ((reservation.getStartDate().after(r.getStartDate()) && reservation.getStartDate().before(r.getEndDate())) ||
                        (reservation.getEndDate().after(r.getStartDate()) && reservation.getEndDate().before(r.getEndDate())) ||
                        (reservation.getStartDate().compareTo(r.getStartDate()) <= 0 && reservation.getEndDate().compareTo(r.getEndDate()) >= 0))
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
