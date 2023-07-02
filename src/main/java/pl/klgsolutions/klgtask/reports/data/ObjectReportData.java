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

    private long reservationDays;

    private int reservationCount;
}
