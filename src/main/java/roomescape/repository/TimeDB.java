package roomescape.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Repository
public class TimeDB {
    // 6단계 : JdbcTemplate을 이용하여 DataSource객체에 접근
    private JdbcTemplate jdbcTemplate;

    public TimeDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // TimeRowmapper 클래스가 RowMapper 인터페이스를 구현
    public class TimeRowmapper implements RowMapper<Time> {
        @Override
        public Time mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            String time = rs.getString("time");

            return new Time(id, time);
        }
    }

    // 데이터 조회하기 : DB에서 전체 데이터 조회하기
    public List<Time> getTimeByDB() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, new TimeRowmapper());
    }


    // 데이터 조회하기 : 특정 데이터 DB에서 조회하기 (id 활용)
    public Time getTimeByDB(Long id) {
        String sql = "SELECT * FROM time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new TimeRowmapper(), id);
    }

    // DB에 시간 정보 추가하기
    public Time saveTimeDB(Time time) {
        String sql = "INSERT INTO time (time) VALUES (?)";
        // KeyHolder 객체를 이용하여 생성된 id값을 받아옴
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, time.getTime());
            return ps;
        }, keyHolder);

        // 생성된 id값을 이용하여 Reservation 객체를 생성하여 반환
        return Time.builder()
                .id(requireNonNull(keyHolder.getKey()).longValue())
                .time(time.getTime())
                .build();
    }

    // 시간 취소
    public void deleteTimeDB(Long id) {
        String sql = "DELETE FROM time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
