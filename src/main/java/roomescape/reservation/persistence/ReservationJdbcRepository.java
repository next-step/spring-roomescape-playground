package roomescape.reservation.persistence;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import roomescape.reservation.domain.Reservation;

@Repository
@Primary
public class ReservationJdbcRepository implements ReservationRepository {

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<Reservation> ReservationRowMapper = (resultSet, rowNum) ->
		new Reservation(
			resultSet.getLong("id"),
			resultSet.getString("name"),
			resultSet.getString("date"),
			resultSet.getString("roomescape/time")
		);

	public ReservationJdbcRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Reservation> findAll() {
		String sql = "select id, name, date, time from reservation";
		return List.copyOf(jdbcTemplate.query(sql, ReservationRowMapper));
	}

	@Override
	public Reservation save(Reservation reservation) {
		String sql = "insert into reservation (name, date, time) values (?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
			ps.setString(1, reservation.getName());
			ps.setString(2, reservation.getDate());
			ps.setString(3, reservation.getTime());
			return ps;
		}, keyHolder);
		return new Reservation(keyHolder.getKey().longValue(), reservation.getName(), reservation.getDate(),
			reservation.getTime());
	}

	@Override
	public Reservation findById(Long id) {
		String sql = "select id, name, date, time from reservation where id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, ReservationRowMapper, id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void delete(Long id) {
		String sql = "delete from reservation where id = ?";
		jdbcTemplate.update(sql, id);
	}
}
