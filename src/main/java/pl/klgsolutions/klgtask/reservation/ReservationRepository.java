package pl.klgsolutions.klgtask.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.klgsolutions.klgtask.reports.data.LandlordDetailData;
import pl.klgsolutions.klgtask.reports.data.ObjectReportData;

import java.sql.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByObjectId(Long id);

    List<Reservation> findAllByObjectNameIgnoreCase(String name);

    List<Reservation> findAllByRenterNameIgnoreCase(String name);

    @Query(
            "SELECT new pl.klgsolutions.klgtask.reports.data.ObjectReportData(" +
                        "r.object.name, CAST(:from AS string), CAST(:to AS string), " +
                        " SUM( " +
                            "CASE " +
                                "WHEN r.startDate BETWEEN :from AND :to AND r.endDate BETWEEN :from AND :to THEN FUNCTION('DATEDIFF', DAY, r.startDate, r.endDate) + 1 " + // using date subtraction with dates from db returns nanoseconds between the dates, so I call HSQLDB native DATEDIFF function with DAY parameter
                                "WHEN :from < r.startDate AND :to BETWEEN r.startDate AND r.endDate THEN FUNCTION('DATEDIFF', DAY, r.startDate, :to) + 1  " +
                                "WHEN :to > r.endDate AND :from BETWEEN r.startDate AND r.endDate THEN FUNCTION('DATEDIFF', DAY, :from, r.endDate) + 1 " +
                                "WHEN :from BETWEEN r.startDate AND r.endDate AND :to BETWEEN r.startDate AND r.endDate THEN FUNCTION('DATEDIFF', DAY, :from, :to) + 1  " + // but here works fine out of the box :)
                                "ELSE 0 " +
                            "END " +
                        "), " +
                        "SUM( " +
                            "CASE " +
                                "WHEN " +
                                    ":from BETWEEN r.startDate AND r.endDate OR " +
                                    ":to BETWEEN r.startDate AND r.endDate OR " +
                                    "(:from < r.startDate AND :to > r.endDate) " +
                                "THEN 1 " +
                                "ELSE 0 " +
                            "END" +
                        ")" +
                    ") " +
                    "FROM Reservation r " +
                    "WHERE LOWER(r.object.name) = LOWER(:objectName) " +
                    "GROUP BY r.object.name"
    )
    ObjectReportData getObjectReportData(@Param("objectName") String objectName, @Param("from") Date from, @Param("to") Date to);

    @Query(
            "SELECT new pl.klgsolutions.klgtask.reports.data.LandlordDetailData(" +
                        "r.landlord.name, COUNT(DISTINCT r.object.name), COUNT(r), SUM(r.cost)" +
                    ") " +
                    "FROM Reservation r " +
                    "WHERE " +
                        ":from BETWEEN r.startDate AND r.endDate OR " +
                        ":to BETWEEN r.startDate AND r.endDate OR " +
                        "(:from < r.startDate AND :to > r.endDate) " +
                    "GROUP BY r.landlord.name"
    )
    List<LandlordDetailData> getLandlordsReportData(@Param("from") Date from, @Param("to") Date to);
}
