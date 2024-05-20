package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import roomescape.model.ReservationRequest;

@Service
@RequiredArgsConstructor
public class UpdatingDAO {

    private final JdbcTemplate jdbcTemplate;

    public void insert(ReservationRequest reservationRequest){

        String sql= "insert into reservation (name, date, time) values (?,?,?)";
        jdbcTemplate.update(sql,reservationRequest.getName()
                ,reservationRequest.getDate()
                ,reservationRequest.getTime());
    }


}
