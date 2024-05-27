package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.DAO.ReservationDAO;
import roomescape.domain.Reservation;

import javax.sql.DataSource;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationDAO reservationDAO;

    @Autowired
    public ReservationService(DataSource dataSource) {
        this.reservationDAO = new ReservationDAO(dataSource);
    }

    public List<Reservation> findAll() {
        return reservationDAO.findAll();
    }

    public Reservation findById(long id) {
        return reservationDAO.findById(id);
    }

    public long save(Reservation reservation) {
        return reservationDAO.save(reservation);
    }

    public long deleteById(long id) {
        return reservationDAO.deleteById(id);
    }
}