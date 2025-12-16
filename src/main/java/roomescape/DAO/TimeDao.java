package roomescape.DAO;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import roomescape.Controller.TimeController;
import roomescape.Domain.Time;

import java.net.URI;
import java.sql.PreparedStatement;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Repository
public class TimeDao {
    private JdbcTemplate jdbcTemplate;
    public TimeDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper
    private final RowMapper<Time> rowMapper = (resultSet, rowNum) ->
            new Time(
                    resultSet.getLong("id"),
                    resultSet.getString("time")
            );

    // Read
    public List<Time> findAll(){
        String sql = "select id,time from time";
        return jdbcTemplate.query(sql,rowMapper);
    }

    // Create
    public Long add(Time time){

        String sql = "insert into time (time) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("Failed to retrieve generated id");
        }

        Long id = key.longValue();
        return id;
    }

    // Delete
    public int deleteByid(Long id){
        String sql = "delete from time where id = ?";
        int deleted = jdbcTemplate.update(sql, id);
        return deleted;
    }


}
