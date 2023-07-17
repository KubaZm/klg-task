package pl.klgsolutions.klgtask.reports.data;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class LandlordDetailData {

    private String name;

    private Long objectCount;

    private Long guestCount;

    private Double income;

    public LandlordDetailData(String name, Long objectCount, Long guestCount, Double income) {
        this.name = name;
        this.objectCount = objectCount;
        this.guestCount = guestCount;
        this.income = income;
    }
}
