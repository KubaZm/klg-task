package pl.klgsolutions.klgtask.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.klgsolutions.klgtask.objectforrent.ObjectForRentService;
import pl.klgsolutions.klgtask.reports.data.LandlordReportData;
import pl.klgsolutions.klgtask.reports.data.ObjectReportData;
import pl.klgsolutions.klgtask.reservation.ReservationRepository;
import pl.klgsolutions.klgtask.reservation.exceptions.ObjectDoesntExistException;

import java.sql.Date;

@Service
public class ReportService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ObjectForRentService objectService;

    public ObjectReportData getObjectReport(String objectName, String from, String to) throws ObjectDoesntExistException {
        if (!objectService.checkIfObjectExistsByName(objectName))
            throw new ObjectDoesntExistException();
        return reservationRepository.getObjectReportData(objectName, Date.valueOf(from), Date.valueOf(to));
    }

    public LandlordReportData getLandlordsReport(String from, String to) {
        return LandlordReportData.builder()
                .from(from)
                .to(to)
                .details(reservationRepository.getLandlordsReportData(Date.valueOf(from), Date.valueOf(to)))
                .build();
    }
}
