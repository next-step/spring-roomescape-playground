    package roomescape.Repository;

    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.core.RowMapper;
    import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
    import org.springframework.jdbc.core.namedparam.SqlParameterSource;
    import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
    import org.springframework.stereotype.Repository;
    import roomescape.Domain.Reservation;
    import roomescape.Domain.Time;

    import javax.sql.DataSource;
    import java.util.List;
    import java.util.NoSuchElementException;

    @Repository
    public class ReservationRepository {
        private JdbcTemplate jdbcTemplate;
        private SimpleJdbcInsert simpleJdbcInsert;

        private final RowMapper<Reservation> reservationRowMapper = (resultSet, rowNum) -> new Reservation
                (
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("date"),
                        new Time(resultSet.getLong("id"), resultSet.getString("time"))
                );

        public ReservationRepository(JdbcTemplate jdbcTemplate, DataSource source) {
            this.jdbcTemplate = jdbcTemplate;
            this.simpleJdbcInsert = new SimpleJdbcInsert(source)
                    .withTableName("reservation")
                    .usingGeneratedKeyColumns("id");
        }


        public Reservation findReservationById(Long id) {
            String sql = """
            SELECT 
                r.id as reservation_id, r.name, r.date, 
                t.id as time_id, t.time as time_value 
                FROM reservation as r 
                inner join time as t on r.time_id = t.id WHERE r.id = ?
            """;;
            return jdbcTemplate.queryForObject(sql, reservationRowMapper, id);
        }


        public List<Reservation> findAllReservations() {
            String sql =
            """
            SELECT 
                r.id as reservation_id, r.name, r.date, 
                t.id as time_id, t.time as time_value 
                FROM reservation as r 
                inner join time as t on r.time_id = t.id
            """;
            return jdbcTemplate.query(sql, reservationRowMapper);
        }

        public Long createReservation(String name, String date, Long timeId) {
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("name", name)
                    .addValue("date", date)
                    .addValue("time_id", timeId);

            return simpleJdbcInsert.executeAndReturnKey(params).longValue();
        }

        public void deleteReservation(Long id) {
            String sql = "delete from reservation where time_id = ? ";
            int rowCheck = jdbcTemplate.update(sql, id);
            if(rowCheck == 0)
            {
                throw new NoSuchElementException("삭제하려는 아이디가 존재하지 않습니다." + id);
            }
        }
    }
