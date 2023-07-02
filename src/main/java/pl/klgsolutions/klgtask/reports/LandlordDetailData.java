package pl.klgsolutions.klgtask.reports;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LandlordDetailData {

    private String name;

    private int objectCount;

    private int guestCount;

    private Float income;
}
