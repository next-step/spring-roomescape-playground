package roomescape.repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
<<<<<<< HEAD
import roomescape.domain.Reservation;
import roomescape.domain.Time;
=======
import roomescape.Reservation;
>>>>>>> next-step/haeun92e0

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationRepository {

<<<<<<< HEAD
    private final JdbcTemplate jdbcTemplate; //SQL을 쉽게 실행하게 해주는 스프링 도구
=======
    private final JdbcTemplate jdbcTemplate;
>>>>>>> next-step/haeun92e0

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
<<<<<<< HEAD
    // 스프링이 JdbcTemplate 객체를 만들어서 Repository에 넣어줌

    //DB에서 가져온 한 줄을 자바 객체로 바꿔줌
    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("reservation_id"),
            rs.getString("name"),
            rs.getObject("date", LocalDate.class), //날짜를 LocalDate 타입으로 꺼냄
            new Time(rs.getLong("time_id"), LocalTime.parse(rs.getString("time_value")))
    );

    public List<Reservation> findAll() { //모든 예약 목록을 가져옴
        String sql = "SELECT r.id as reservation_id, r.name, r.date, t.id as time_id, t.time as time_value " +
                "FROM reservation as r inner join time as t on r.time_id = t.id";
        return jdbcTemplate.query(sql, rowMapper); //SQL을 실행하고 결과 여러 줄을 각각 rowMapper로 Reservation 객체로 바꿔서 리스트로 반환
    }

    public Long save(String name, LocalDate date, Long timeId) {//예약을 DB에 저장하는 메서드
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder(); //자동 생성된 id를 받기 위한 객체

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"}); //INSERT 후 자동 생성된 id 값을 돌려줌
            ps.setString(1, name);
            ps.setObject(2, date.toString());
            ps.setObject(3, timeId);
            return ps; //완성된 SQL 실행 준비 객체를 반환
        }, keyHolder); // SQL 실행 후 자동 생성된 id를 keyholder에 담음

        return keyHolder.getKey().longValue(); //DB가 자동으로 만든 예약 id를 Long 타입으로 반환
    }

    public boolean existsById(Long id) {//해당 id가 존재하는지
        String sql = "SELECT COUNT(1) FROM reservation WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        return count != null && count > 0;
    }

    // 특정 시간 ID(timeId)를 물고 있는 예약 데이터가 단 하나라도 존재하는지 확인
    public boolean existsByTimeId(Long timeId) {
        String sql = "SELECT COUNT(1) FROM reservation WHERE time_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, timeId);
        return count != null && count > 0;
    }

    public int countByDateAndTimeId(LocalDate date, Long timeId) {//특정 날짜+ 특정 시간에 이미 예약이 있는지 확인
        return jdbcTemplate.queryForObject("SELECT COUNT(1) FROM reservation WHERE date = ? AND time_id = ?",
                Integer.class, date.toString(), timeId);
    }
    //예약 id로 예약을 삭제하는 메서드(실제 삭제된 데이터의 개수를 반환함)
    public int deleteById(Long id) {
        String sql = "SELECT COUNT(1) FROM reservation WHERE id = ?";
        return jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
=======

    private final RowMapper<Reservation> rowMapper = (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getObject("date", LocalDate.class),
            rs.getObject("time", LocalTime.class)
    );

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Long save(String name, LocalDate date, LocalTime time) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, name);
            ps.setObject(2, date);
            ps.setObject(3, time);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public int countById(Long id) {
        String sql = "SELECT COUNT(1) FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public int countByDateAndTime(LocalDate date, LocalTime time) {
        String sql = "SELECT COUNT(1) FROM reservation WHERE date = ? AND time = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, date, time);
        return count != null ? count : 0;
>>>>>>> next-step/haeun92e0
    }
}
