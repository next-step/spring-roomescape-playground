package roomescape.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.DTO.ReservationResponse;
import roomescape.DTO.SaveReservationRequest;
import roomescape.Model.JdbcReservationRepository;
import roomescape.Model.JdbcTimeRepository;
import roomescape.Model.Reservation;
import roomescape.Model.Time;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private final JdbcReservationRepository jdbcReservationRepository;

    @Autowired
    private final JdbcTimeRepository jdbcTimeRepository;

    public ReservationService(JdbcReservationRepository jdbcReservationRepository, JdbcTimeRepository jdbcTimeRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
        this.jdbcTimeRepository = jdbcTimeRepository;
    }

    public List<Reservation> findAll() {
        return jdbcReservationRepository.findAll();
    }

    public Reservation findById(Long id) {
        return jdbcReservationRepository.findWithId(id);
    }

    public Reservation save(SaveReservationRequest request) {
        Time time = jdbcTimeRepository.findById(request.time());
        Reservation reservation = new Reservation(request.name(), request.date(), time);
        return jdbcReservationRepository.save(reservation);
    }

    public void delete(Long id) {
        jdbcReservationRepository.deleteById(id);
    }
}
