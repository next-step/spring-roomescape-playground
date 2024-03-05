package roomescape.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.DTO.ReservationRequestDTO;
import roomescape.DTO.ReservationResponseDTO;
import roomescape.Domain.Reservation;
import roomescape.Domain.Time;
import roomescape.Repository.ReservationRepository;
import roomescape.Repository.TimeRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @Override
    public List<ReservationResponseDTO> findAllReservations() {
        return reservationRepository.findAllReservations().stream()
                .map(ReservationResponseDTO::from).toList();
    }

    @Override
    public ReservationResponseDTO findReservationById(Long id) {
        Reservation reservation = reservationRepository.findReservationById(id);

        if (reservation == null || reservation.isEmpty()) {
            throw new NoSuchElementException("찾으려는 아이디가 존재하지 않습니다." + id);
        }

        return ReservationResponseDTO.from(reservation);
    }

    @Override
    public Long createReservation(ReservationRequestDTO reservationRequest) {
        Time time = timeRepository.findTimeReservationById(reservationRequest.getTime());
        return reservationRepository.createReservation(reservationRequest.getName(), reservationRequest.getDate(), time.getId());
    }

    @Override
    public void deleteReservationById(Long id) {
        reservationRepository.deleteReservation(id);
    }
}
