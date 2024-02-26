package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReservationDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate, DataSource source){
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<ReservationResponseDto> rowMapper = (resultSet, rowNum) -> {
        ReservationResponseDto reservation = new ReservationResponseDto(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("date"),
                resultSet.getString("time")
        );
        return reservation;
    };

    public List<ReservationResponseDto> findAll(){
        String sql = "SELECT * FROM reservation";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public ReservationResponseDto findById(Long id){
        String sql = "SELECT * FROM reservation where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public ReservationResponseDto insert(ReservationRequestDto reservationRequest){
        SqlParameterSource params = new BeanPropertySqlParameterSource(reservationRequest);
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return new ReservationResponseDto(id, reservationRequest.name(),
                reservationRequest.date(), reservationRequest.time());
    }

    public void delete(Long id){
        jdbcTemplate.update("delete from reservation where id = ?", id);
    }
}
