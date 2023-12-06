package roomescape.domain.reservation.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import roomescape.domain.reservation.entity.Time;
import roomescape.global.BusinessException;

import static roomescape.global.ErrorCode.TIME_NOT_FOUND;


@Repository
@AllArgsConstructor
public class TimesRepositoryJdbc implements TimesRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Time save(final LocalTime time) {
        final String sql = "INSERT INTO TIME (time) VALUES(?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"id"});
            pstmt.setTime(1, java.sql.Time.valueOf(time));
            return pstmt;
        }, keyHolder);

        return Time.builder()
                .id(keyHolder.getKey().longValue())
                .time(time)
                .build();
    }

    @Override
    public List<Time> findAll() {
        final String sql = "SELECT * FROM TIME";
        return jdbcTemplate.queryForStream(sql, (rs, rowNum) -> Time.builder()
                        .id(rs.getLong("id"))
                        .time(rs.getTime("time").toLocalTime())
                        .build())
                .toList();
    }

    @Override
    public Optional<Time> findById(final Long id) {
        final String sql = "SELECT * FROM TIME WHERE id = ?";
        return jdbcTemplate.queryForStream(sql, (rs, rowNum) -> Time.builder()
                                .id(rs.getLong("id"))
                                .time(rs.getTime("time").toLocalTime())
                                .build(),
                        id)
                .findFirst();
    }

    @Override
    public void deleteById(final Long id) {
        final String sql = "DELETE FROM TIME WHERE id = ?";
        final int rowCount = jdbcTemplate.update(sql, id);

        if (rowCount == 0) {
            throw new BusinessException(id, "id", TIME_NOT_FOUND);
        }
    }
}
