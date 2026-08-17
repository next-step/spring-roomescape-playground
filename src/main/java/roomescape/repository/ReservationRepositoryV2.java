package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.repository.sql.JdbcSQL;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepositoryV2 implements ReservationRepository {

    private JdbcTemplate jdbcTemplate;

    public ReservationRepositoryV2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(JdbcSQL.FIND_ALL.getSql(),
                (resultSet, rowNum) ->
                        // 각 row를 바인딩할 Entity 객체 생성
                        Reservation.toEntityWithId(
                                resultSet.getLong("id"),
                                new Reservation(
                                        resultSet.getString("name"),
                                        LocalDate.parse(resultSet.getString("date")),
                                        LocalTime.parse(resultSet.getString("time"))
                                )
                        )

                );
    }

    @Override
    public void delete(Reservation reservation) {

    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return Optional.of(jdbcTemplate.queryForObject(JdbcSQL.FIND_BY_ID.getSql(), Reservation.class, reservationId));
    }
}
