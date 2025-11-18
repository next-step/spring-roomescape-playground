package roomescape.repository; // 패키지는 그대로 둡니다.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation; // Reservation 모델 import

import java.sql.PreparedStatement;
import java.util.List;
@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate=jdbcTemplate;
    }

    private final RowMapper<Reservation> reservationRowMapper = (rs,rowNum)->{
        return new Reservation(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("date"),
                rs.getString("time")
        );
    };

    public List<Reservation> findAll()
    {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql,reservationRowMapper);
    }

    public Reservation save(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();// DB에서 생성해준 auto-increment를 받아서 와준다.

        jdbcTemplate.update(connection ->{
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2,reservation.getDate());
            ps.setString(3,reservation.getName());
            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        return new Reservation(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE id = ?";

        Integer count = jdbcTemplate.queryForObject(sql,Integer.class, id);
        return count>0;
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id =?";
        jdbcTemplate.update(sql, id);
    }

    public void clear() {
        // DELETE FROM reservations; -> 데이터를 모두 지웁니다. (느림)
        //String sql = "DELETE FROM reservations";
        // TRUNCATE TABLE reservations; -> 테이블을 통째로 비웁니다. (빠름)
        // 테스트용 clear는 TRUNCATE가 ID 카운터(auto_increment)까지 1로 초기화해줘서 더 좋습니다.
        String sql = "TRUNCATE TABLE reservation";
        jdbcTemplate.update(sql);
    }
}
