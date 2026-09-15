package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.controller.dto.ReservationRequestDto;
import roomescape.controller.dto.ReservationResponseDto;
import roomescape.exception.NotFoundException;
import roomescape.model.Reservation;
import roomescape.model.Time;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public ReservationResponseDto create(ReservationRequestDto reservationDTO) {
        Time time = timeRepository.findById(reservationDTO.time());
        if (time == null) {
            throw new NotFoundException("선택한 시간을 찾을 수 없습니다.");
        }
        Reservation reservation = Reservation.create(reservationDTO.name(), reservationDTO.date(), time);
        return new ReservationResponseDto(reservationRepository.save(reservation));
    }

    public List<ReservationResponseDto> read() {
        List<ReservationResponseDto> responseDtos = new ArrayList<>();
        List<Reservation> reservations = reservationRepository.findAllReservations();
        for (Reservation reservation : reservations) {
            responseDtos.add(new ReservationResponseDto(reservation));
        }
        return responseDtos;
    }

    public void delete(Long id) {
        int affectedRow = reservationRepository.delete(id);
        if (affectedRow == 0) {
            throw new NotFoundException("삭제 할 예약이 없습니다.");
        }
    }
}
