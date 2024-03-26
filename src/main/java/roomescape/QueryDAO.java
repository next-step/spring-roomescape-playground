package roomescape;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QueryDAO {

    // JdbcTemplate 을 사용하여 SQL 문을 실행할 수 있다.
    // JdbcTemplate 은 PreparedStatement 를 생성하고 SQL 매개변수를 설정하며, ResultSet 을 처리할 수 있다.
    private JdbcTemplate jdbcTemplate;

    public QueryDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

//    //데이터베이스 쿼리를 통해 조회한 예약 수
//    public int count() {
//        String sql = "select count(*) from reservation";
//        return jdbcTemplate.queryForObject(sql, Integer.class);
//    }

    public List<Reservation> findAllReservations() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Reservation reservation = new Reservation(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("date"),
                            resultSet.getString("time")
                    );
                    return reservation;
                });
    }


}
