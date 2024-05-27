package roomescape.repository.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import roomescape.model.Reservation;
import roomescape.model.ReservationRequestDTO;
import roomescape.model.ReservationResponseDTO;
import roomescape.model.Time;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public List<ReservationResponseDTO> findAll() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponseDTO> result = reservations.stream()
                .map(ReservationResponseDTO::ReservationToResponse).toList();
        return result;
    }

    public ReservationResponseDTO reservationAdd(ReservationRequestDTO reservationRequestDTO) {
        Time time = timeRepository.findById(reservationRequestDTO.getTime());

        Reservation reservation = new Reservation(reservationRequestDTO.getName(), reservationRequestDTO.getDate(), time);

        Reservation result = reservationRepository.reservationAdd(reservation);

        ReservationResponseDTO response = new ReservationResponseDTO(result.getId(), result.getName(), result.getDate(), result.getTime());
        return response;
    }

    public void delete(int id) {
        reservationRepository.delete(id);
    }
}
