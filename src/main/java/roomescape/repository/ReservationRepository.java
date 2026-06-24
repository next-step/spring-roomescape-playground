package roomescape.repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate; //SQL을 쉽게 실행하게 해주는 스프링 도구

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
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

    public int countByDateAndTimeId(LocalDate date, Long timeId) {//특정 날짜+ 특정 시간에 이미 예약이 있는지 확인
        return jdbcTemplate.queryForObject("SELECT COUNT(1) FROM reservation WHERE date = ? AND time_id = ?",
                Integer.class, date.toString(), timeId);
    }
    //예약 id로 예약을 삭제하는 메서드
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
