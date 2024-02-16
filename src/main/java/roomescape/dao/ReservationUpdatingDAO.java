package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.dto.ReservationAddRequest;
import roomescape.exception.NotFoundReservationException;
import roomescape.domain.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class ReservationUpdatingDAO {
    private static ReservationUpdatingDAO instance = null;
    private final JdbcTemplate jdbcTemplate;
    private final TimeQueryingDAO timeQueryingDAO;
    private static final Logger log = LoggerFactory.getLogger(ReservationUpdatingDAO.class);

    @Autowired
    protected ReservationUpdatingDAO(JdbcTemplate jdbcTemplate, TimeQueryingDAO timeQueryingDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.timeQueryingDAO = timeQueryingDAO;
    }

    public Long insertWithKeyHolder(ReservationAddRequest reservation) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", timeQueryingDAO.findTimeById(reservation.getTime()).getId());

        Number key = jdbcInsert.executeAndReturnKey(namedParameters);

        return key.longValue();
    }

    public void delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        int num = jdbcTemplate.update(sql, id);
        if (num == 0) { // 삭제할 대상이 없는 경우
            String errorMessage = "Reservation is not found in DB with id: " + id;
            log.error(errorMessage);
            throw new NotFoundReservationException(errorMessage);
        }
    }

}
