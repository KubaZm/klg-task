package pl.klgsolutions.klgtask.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.klgsolutions.klgtask.person.Person;
import pl.klgsolutions.klgtask.person.PersonService;
import pl.klgsolutions.klgtask.reports.data.LandlordDetailData;
import pl.klgsolutions.klgtask.reports.data.LandlordReportData;
import pl.klgsolutions.klgtask.reports.data.ObjectReportData;
import pl.klgsolutions.klgtask.reservation.ReservationRepository;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PersonService personService;

    public ObjectReportData getObjectReport(String objectName, String from, String to) {
        Date fromDate = Date.valueOf(from);
        Date toDate = Date.valueOf(to);

        AtomicLong reservationDays = new AtomicLong();
        AtomicInteger reservationCount = new AtomicInteger();

        reservationRepository
                .findAllByObjectNameIgnoreCase(objectName)
                .forEach(reservation -> {
                    //the reservation dates are between the range of query parameters
                    if (reservation.getStartDate().after(fromDate) && reservation.getEndDate().before(toDate)) {
                        reservationDays.addAndGet(ChronoUnit.DAYS.between(reservation.getStartDate().toLocalDate(), reservation.getEndDate().toLocalDate()) + 1);
                        reservationCount.getAndIncrement();
                    }
                    //the "from" parameter is before reservation startDate and the "to" parameter is between reservation's startDate and endDate
                    else if (reservation.getStartDate().after(fromDate) && reservation.getStartDate().compareTo(toDate) <= 0 &&
                            reservation.getEndDate().compareTo(toDate) >= 0) {
                        reservationDays.addAndGet(ChronoUnit.DAYS.between(reservation.getStartDate().toLocalDate(), toDate.toLocalDate()) + 1);
                        reservationCount.getAndIncrement();
                    }
                    //the "to" parameter is after reservation endDate and the "from" parameter is between reservation's startDate and endDate
                    else if (reservation.getStartDate().compareTo(fromDate) <= 0 && reservation.getEndDate().compareTo(fromDate) >= 0 &&
                            reservation.getEndDate().before(toDate)) {
                        reservationDays.addAndGet(ChronoUnit.DAYS.between(fromDate.toLocalDate(), reservation.getEndDate().toLocalDate()) + 1);
                        reservationCount.getAndIncrement();
                    }
                    //the range of query parameters is between the reservation dates
                    else if (reservation.getStartDate().compareTo(fromDate) <= 0 && reservation.getEndDate().compareTo(toDate) >= 0) {
                        reservationDays.addAndGet(ChronoUnit.DAYS.between(fromDate.toLocalDate(), toDate.toLocalDate()) + 1);
                        reservationCount.getAndIncrement();
                    }
                });

        return ObjectReportData.builder()
                .objectName(objectName)
                .from(from)
                .to(to)
                .reservationDays(reservationDays.get())
                .reservationCount(reservationCount.get())
                .build();
    }

    public LandlordReportData getLandlordsReport(String from, String to) {
        Date fromDate = Date.valueOf(from);
        Date toDate = Date.valueOf(to);

        List<LandlordDetailData> landlordData = new ArrayList<>();

        List<String> landlords = personService.getAllPeople().stream().map(Person::getName).toList();

        for (String landlord : landlords) {
            LandlordDetailData data = new LandlordDetailData();
            data.setName(landlord);
            AtomicInteger guestCount = new AtomicInteger();
            AtomicReference<Float> income = new AtomicReference<>(0f);

            List<String> objects = new ArrayList<>();
            reservationRepository.findAllByLandlordNameIgnoreCase(landlord).forEach(reservation -> {

                //the reservation dates are between the range of query parameters or "from" or "to" parameter is between the reservation's startDate and endDate
                if ((reservation.getStartDate().after(fromDate) && reservation.getEndDate().before(toDate)) ||
                        (reservation.getStartDate().compareTo(fromDate) <= 0 && reservation.getEndDate().compareTo(fromDate) >= 0) ||
                        (reservation.getStartDate().compareTo(toDate) <= 0 && reservation.getEndDate().compareTo(toDate) >= 0)) {
                    if (!objects.contains(reservation.getObject().getName()))
                        objects.add(reservation.getObject().getName());
                    guestCount.getAndIncrement();
                    income.updateAndGet(v -> v + reservation.getCost());
                }


            });
            data.setObjectCount(objects.size());
            data.setGuestCount(guestCount.get());
            data.setIncome(income.get());
            landlordData.add(data);
        }

        return LandlordReportData.builder()
                .from(from)
                .to(to)
                .details(landlordData)
                .build();
    }
}
