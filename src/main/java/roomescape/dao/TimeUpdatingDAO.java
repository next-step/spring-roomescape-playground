package roomescape.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import roomescape.domain.Time;
import roomescape.exception.NotFoundException;

@Component
public class TimeUpdatingDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public TimeUpdatingDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingGeneratedKeyColumns("id");
    }

    public Number save(Time time){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("time", time.getTime());

        return simpleJdbcInsert.executeAndReturnKey(parameters);
    }

    public int delete(long id){
        String sql = "DELETE FROM time WHERE id = ?";
        int num = jdbcTemplate.update(sql, id);

        if(num == 0){
            throw new NotFoundException("Time is not found with id: " + id);
        }

        return num;
    }
}
