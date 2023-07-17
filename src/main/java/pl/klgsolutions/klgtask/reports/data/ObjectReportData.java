package pl.klgsolutions.klgtask.reports.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ObjectReportData {

    private String objectName;

    private String from;

    private String to;

    private Long reservationDays;

    private Long reservationCount;

    public ObjectReportData(String objectName, String from, String to, Long reservationDays, Long reservationCount) {
        this.objectName = objectName;
        this.from = from;
        this.to = to;
        this.reservationDays = reservationDays;
        this.reservationCount = reservationCount;
    }
}
