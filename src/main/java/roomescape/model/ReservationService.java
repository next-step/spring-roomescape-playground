package roomescape.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import roomescape.exception.InvalidReservationParameterException;
import roomescape.exception.NotFoundReservationException;

import java.sql.PreparedStatement;
import java.util.List;

@Service
public class ReservationService {
    private final JdbcTemplate jdbcTemplate;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(JdbcTemplate jdbcTemplate, ReservationRepository reservationRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation addReservation(ReservationRequest reservationRequest) {
        if (reservationRequest.getName() == null || reservationRequest.getName().isEmpty() ||
                reservationRequest.getDate() == null || reservationRequest.getDate().isEmpty() ||
                reservationRequest.getTime() == null || reservationRequest.getTime().isEmpty()) {
            throw new InvalidReservationParameterException("예약 내용에 누락된 부분이 있습니다.");
        }

        Reservation reservation = new Reservation(reservationRequest.getName(), reservationRequest.getDate(),reservationRequest.getTime());

        String sql = "INSERT INTO reservation (name,date,time) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;
        }, keyHolder);

        Number generatedId = keyHolder.getKey();
        if (generatedId != null) {
            reservation.setId(generatedId.longValue());
        }
        return reservation;
    }

    public void deleteReservation(Long id) {
        int rowAffected = reservationRepository.deleteById(id);
        if (rowAffected == 0) {
            throw new NotFoundReservationException("삭제하려는 예약이 없습니다.");
        }
    }
}
