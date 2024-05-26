package roomescape.db;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationRequest;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;


    public final RowMapper<ReservationEntity> actorRowMapper = (resultSet, rowNum) -> {
        ReservationEntity reservationEntity = new ReservationEntity(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservationEntity;
    };

    @Override
    public List<ReservationEntity> findAllList() {

        String sql = "select * from reservation";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    @Override
    public int countReservation() {

        String sql = "select count(*) from reservation";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public ReservationEntity findReservationById(Long id) {

        String sql = "select * from reservation where id=?";
        return jdbcTemplate.queryForObject(sql, actorRowMapper, id);
    }

    @Override
    public ReservationEntity insertReservation(ReservationRequest reservationRequest) {

        String sql = "insert into reservation (name, date, time) values (?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservationRequest.getName());
            ps.setObject(2, reservationRequest.getDate());
            ps.setObject(3, reservationRequest.getTime());
            return ps;
        }, keyHolder);

        long generatedKey = keyHolder.getKey().longValue();

        return findReservationById(generatedKey);


    }

    @Override
    public void deleteReservation(Long id) {

        String sql = "delete from reservation where id=?";

        int rowAffected = jdbcTemplate.update(sql, id);

        if (rowAffected == 0) throw new RuntimeException("[ERROR] 존재하지 않는 id입니다");
    }
}
