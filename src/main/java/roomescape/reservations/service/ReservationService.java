package roomescape.reservations.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.reservations.exception.ReservationException;
import roomescape.reservations.dto.request.ReservationRequest;
import roomescape.reservations.dto.response.ReservationResponse;
import roomescape.reservations.model.Reservation;
import roomescape.reservations.repository.JdbcReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {

    private static final int MAX_CAPACITY_PER_TIME = 5;

    private final JdbcReservationRepository jdbcReservationRepository;

    public ReservationService(JdbcReservationRepository jdbcReservationRepository) {
        this.jdbcReservationRepository = jdbcReservationRepository;
    }

    public List<ReservationResponse> getAllReservations() {
        List<Reservation> reservations = jdbcReservationRepository.getAllReservations();

        return reservations.stream()
                .map(this::convertIntoResponseDTO)
                .toList();
    }

    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = jdbcReservationRepository.getReservationById(id)
                .orElseThrow(() -> new ReservationException("일치하는 예약건이 없어요! 다시 확인해주세요!"));

        return convertIntoResponseDTO(reservation);
    }

    @Transactional
    public ReservationResponse createReservation(ReservationRequest newReservation) {
        int currentTimeReservationCount = jdbcReservationRepository.getReservationCountInTimeSlot(newReservation.date(), newReservation.time());
        validateCapacityPerTime(currentTimeReservationCount);

        Reservation reservation = new Reservation(
                null,
                newReservation.name(),
                newReservation.date(),
                newReservation.time()
        );

        validateDuplicateReservation(reservation);

        Long createdReservationId = jdbcReservationRepository.createReservation(reservation);

        Reservation createdReservation = new Reservation(
                createdReservationId,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );

        return convertIntoResponseDTO(createdReservation);
    }

    public void deleteReservationById(Long id) {
        int changedRowsCount = jdbcReservationRepository.deleteReservationById(id);
        if (changedRowsCount == 0) {
            throw new ReservationException("일치하는 예약건이 없어요!");
        }
    }

    private void validateCapacityPerTime(int currentTimeReservationCount) {
        if (currentTimeReservationCount >= MAX_CAPACITY_PER_TIME) {
            throw new ReservationException("선택하신 시간대는 예약이 마감되었어요! 다른 시간대를 찾아봐주세요! (정원: " + MAX_CAPACITY_PER_TIME + "명)");
        }
    }

    private void validateDuplicateReservation(Reservation newReservation) {
        boolean existsBySameUserAtSameTime = jdbcReservationRepository
                .existsDuplicateReservationWithSameUser(newReservation.getDate(), newReservation.getTime(), newReservation.getName());

        if (existsBySameUserAtSameTime) {
            throw new ReservationException("이미 동일한 시간에 동일한 이름으로 예약건이 있어요!");
        }
    }

    private ReservationResponse convertIntoResponseDTO(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
