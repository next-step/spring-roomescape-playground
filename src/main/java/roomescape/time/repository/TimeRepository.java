package roomescape.time.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import roomescape.time.domain.Time;

import java.time.LocalDate;
import java.util.List;

public interface TimeRepository extends JpaRepository<Time, Long> {
    @Query(
            "SELECT " +
                    "r.id as reservation_id, " +
                    "r.name, " +
                    "r.date, " +
                    "t.id as time_id, " +
                    "t.time as time_value " +
                    "FROM Reservation as r INNER JOIN Time as t ON r.time.id = t.id " +
                    "WHERE t.id = :timeId"
    )
    List<Object[]> findReservationsWithTimeById(@Param("timeId") Long timeId);
}
