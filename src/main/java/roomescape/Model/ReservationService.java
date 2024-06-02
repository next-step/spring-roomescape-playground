package roomescape.Model;

import org.springframework.stereotype.Service;
import roomescape.Repository.ReservationRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final JdbcTemplate jdbcTemplate;

    public ReservationService(ReservationRepository reservationRepository, JdbcTemplate jdbcTemplate) {
        this.reservationRepository = reservationRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reservation> getReservationsList() {
        return reservationRepository.getAllReservations();
    }

    public Reservation saveReservation(RequestReservationDTO reservation) {
        String name = reservation.getName();
        String date = reservation.getDate();
        int timeId = reservation.getTime();

        // timeId를 사용하여 ReservationTime 객체 생성
        ReservationTime time = findTimeById(timeId);

        Reservation newReservation = new Reservation(null, name, date, time);
        Long id = reservationRepository.createReservation(newReservation);
        return reservationRepository.getReservationById(id);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteReservationById(id);
    }

    public Reservation viewReservation(Long id) {
        return reservationRepository.getReservationById(id);
    }

    private ReservationTime findTimeById(int timeId) {
        String sql = "SELECT id, time FROM time WHERE id = ?";
        RowMapper<ReservationTime> rowMapper = (rs, rowNum) -> new ReservationTime(rs.getLong("id"), rs.getString("time"));
        return jdbcTemplate.queryForObject(sql, rowMapper, timeId);
    }
}
