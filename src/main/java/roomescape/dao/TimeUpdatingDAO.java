package roomescape.dao;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.dto.TimeAddRequest;
import roomescape.exception.NotFoundTimeException;
import roomescape.domain.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class TimeUpdatingDAO {
    private static TimeUpdatingDAO instance;
    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(TimeQueryingDAO.class);


    protected TimeUpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Long insertWithKeyHolder(TimeAddRequest time) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("time", time.getTime());

        Number key = jdbcInsert.executeAndReturnKey(namedParameters);

        return key.longValue();
    }

    public void delete(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        int num = jdbcTemplate.update(sql, id);
        if (num == 0) { // 삭제할 대상이 없는 경우
            String errorMessage = "Time is not found in DB with id: " + id;
            log.error(errorMessage);
            throw new NotFoundTimeException(errorMessage);
        }
    }
}
