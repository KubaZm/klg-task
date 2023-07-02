package pl.klgsolutions.klgtask.reports;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LandlordReportData {

    private String from;

    private String to;

    private List<LandlordDetailData> details;
}
