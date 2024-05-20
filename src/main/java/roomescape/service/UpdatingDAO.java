package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import roomescape.model.ReservationRequest;

@Service
@RequiredArgsConstructor
public class UpdatingDAO {

    private final JdbcTemplate jdbcTemplate;

    public void insert(ReservationRequest reservationRequest) {

        String sql = "insert into reservation (name, date, time) values (?,?,?)";
        jdbcTemplate.update(sql, reservationRequest.getName()
                , reservationRequest.getDate()
                , reservationRequest.getTime());
    }

    public void delete(Long id) {

        String sql = "delete from reservation where id=?";

        int rowAffected = jdbcTemplate.update(sql, id);

        if (rowAffected == 0) throw new RuntimeException("[ERROR] 존재하지 않는 id입니다");
    }
}
