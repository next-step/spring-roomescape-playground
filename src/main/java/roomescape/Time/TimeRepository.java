package roomescape.Time;

import error.Exception400;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class TimeRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Time insertTime(Time time) {
        Time newTime = time.toEntity(time);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "insert into time (time) values (?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps=  connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, newTime.getTime());
            return  ps;
        },keyHolder);

        Long id = keyHolder.getKey().longValue();
        newTime.setId(id);
        return newTime;
    }
    public Time findById(Long id){
        String sql = "select id, time from time where id = ?";

        try {
            Time time = jdbcTemplate.queryForObject(sql, new Object[]{id}, (ps, rowNum)-> {
                Long Timeid = ps.getLong("id");
                String TimeVal = ps.getString("time");
                return new Time(Timeid, TimeVal);
            });
            return time;
        } catch (EmptyResultDataAccessException e) {
            // 찾는 값이 없을 경우 예외 처리
            throw new Exception400("존재하지 않는 time입니다."); // Exception400은 예시일 뿐 실제 예외 클래스에 따라서 사용
        }
    }

    public List<Time> SelectAll() {
        String sql = "select id, time from time";
        List<Time> newTime = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Time time = new Time(
                    rs.getLong("id"),
                    rs.getString("time"));
            return time;
        });
        return newTime;
    }

    public void DeleteById(Long id) {
        String sql  = "delete from time where id = ?";
        int num = jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
