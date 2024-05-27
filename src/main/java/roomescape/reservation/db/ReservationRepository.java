package roomescape.reservation.db;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.time.db.TimeEntity;

import java.sql.PreparedStatement;
import java.util.List;

@Primary
@Repository
@RequiredArgsConstructor
public class ReservationRepository implements ReservationRepositoryImpl {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<ReservationEntity> rowMapper = (rs, rowNum)->{
        TimeEntity timeEntity = new TimeEntity(rs.getLong("time_id") , rs.getString("time_value"));
        ReservationEntity reservationEntity = ReservationEntity.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .date(rs.getString("date"))
                .timeEntity(timeEntity)
                .build();

        return reservationEntity;
    };



    @Override
    public List<ReservationEntity> findAll() {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r INNER JOIN time as t ON r.time_id = t.id";

        return jdbcTemplate.query(sql,rowMapper);
    }

    @Override
    public ReservationEntity save(ReservationEntity reservationEntity) {
        String sql = "INSERT INTO reservation (name,date,time_id) VALUES(?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, reservationEntity.getName());
            ps.setObject(2,reservationEntity.getDate());
            ps.setLong(3, reservationEntity.getTimeEntity().getId());
            return ps;

        },keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        reservationEntity.setId(generatedId);
        return reservationEntity;

    }

    @Override
    public ReservationEntity findById(Long id) {
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r INNER JOIN time as t ON r.time_id = t.id " +
                "WHERE r.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
