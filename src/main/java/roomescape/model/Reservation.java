package roomescape.model;

import roomescape.dto.ReservationDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public record Reservation(long id, String name, String date, String time) {
    public Reservation(ResultSet resultSet, int rowNum) throws SQLException {
        this(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time"));
    }

    public Reservation(long id, ReservationDto reservationDto) {
        this(id, reservationDto.name(), reservationDto.date(), reservationDto.time());
    }
}
