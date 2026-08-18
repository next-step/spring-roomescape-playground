package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.entity.Reservation;
import roomescape.repository.sql.JdbcSQL;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private JdbcTemplate jdbcTemplate;

    public ReservationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reservation save(Reservation reservation) {
        // JDBC API에서 반환할 기본 키 바인더
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                // PreparedStatementCreator 익명 객체 빌드
                connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    JdbcSQL.SAVE.getSql(),
                    new String[]{"id"}
            );

            // JDBC row 인덱싱은 1부터 시작. 값 바인딩
            preparedStatement.setString(1, reservation.getName());
            preparedStatement.setString(2, reservation.getDate().toString());
            preparedStatement.setString(3, reservation.getTime().toString());

            return preparedStatement;
        },  keyHolder);

        Long id = keyHolder.getKey().longValue();

        return Reservation.withId(id, reservation);
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query(JdbcSQL.FIND_ALL.getSql(),
                (resultSet, rowNum) ->
                        // 각 row를 바인딩할 Entity 객체 생성
                        Reservation.withId(
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
        if (reservation == null) {
            throw new IllegalArgumentException();
        }
        jdbcTemplate.update(JdbcSQL.DELETE.getSql(), reservation.getId());
    }

    @Override
    public Optional<Reservation> findById(Long reservationId) {
        return jdbcTemplate.query(
                        JdbcSQL.FIND_BY_ID.getSql(),
                        (resultSet, rowNum) -> Reservation.withId(
                                resultSet.getLong("id"),
                                new Reservation(
                                        resultSet.getString("name"),
                                        LocalDate.parse(resultSet.getString("date")),
                                        LocalTime.parse(resultSet.getString("time"))
                                )
                        ), reservationId
                ).stream().findFirst();
    }
}
