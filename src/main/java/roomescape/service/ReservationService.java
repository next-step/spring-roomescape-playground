package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.CreateReservationRequestDto;
import roomescape.dto.CreateReservationResponseDto;
import roomescape.dto.ReadReservationDto;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundTimeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findReservationById(id)
                .orElseThrow(() -> new NotFoundReservationException("Reservation with id " + id + " not found"));
        reservationRepository.delete(id);
    }

    public CreateReservationResponseDto createReservation(CreateReservationRequestDto reservationRequestDto) {
        Time time = timeRepository.findTimeById(reservationRequestDto.getTimeId())
                .orElseThrow(() -> new NotFoundTimeException("Time with id " + reservationRequestDto.getTimeId() + " not found"));

        Reservation newReservation = new Reservation(null, reservationRequestDto.getName(), reservationRequestDto.getDate(), time);

        Reservation savedReservation = reservationRepository.saveReservation(newReservation);
        return convertToCreateReservationResponseDto(savedReservation);
    }

    public List<ReadReservationDto> findAllReservations() {
        return reservationRepository.findAllReservations().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CreateReservationResponseDto convertToCreateReservationResponseDto(Reservation reservation) {
        return new CreateReservationResponseDto(reservation.getReservationId(), reservation.getName(), reservation.getDate(), reservation.getTime().getTime());
    }

    private ReadReservationDto convertToDto(Reservation reservation) {
        return new ReadReservationDto(reservation.getName(), reservation.getDate(), reservation.getTime().getId());
    }

}