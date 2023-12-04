package roomescape.domain.reservation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import roomescape.domain.reservation.entity.Reservation;
import roomescape.domain.time.dao.TimeRepository;
import roomescape.domain.time.entity.Time;

@Component
@RequiredArgsConstructor
public class ReservationMapper implements RowMapper<Reservation> {
    private final TimeRepository timeRepository;

    @Override
    public Reservation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long timeId = rs.getLong("time_id");
        Time time = timeRepository.findById(timeId);
        return Reservation.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .date(rs.getDate("date").toLocalDate())
                .time(time)
                .build();
    }
}
