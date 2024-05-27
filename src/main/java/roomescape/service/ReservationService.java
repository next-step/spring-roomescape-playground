package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.ReservationEntity;
import roomescape.domain.TimeEntity;
import roomescape.dto.ReservationDTO;
import roomescape.exception.InvalidReservationInputException;
import roomescape.exception.InvalidReservationTimeException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository, TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::entityToDTO)
                .toList();
    }

    public ReservationDTO addReservation(ReservationDTO reservationDTO) {
        validateReservationInput(reservationDTO);

        TimeEntity timeEntity = timeRepository.findById(reservationDTO.timeId())
                .orElseThrow(() -> new InvalidReservationTimeException("유효하지 않은 시간입니다."));

        LocalDateTime reservationDateTime = LocalDateTime.parse(reservationDTO.date() + "T" + timeEntity.time());
        validateReservationTime(reservationDateTime);

        ReservationEntity entity = new ReservationEntity(null, reservationDTO.name(), reservationDTO.date(), timeEntity);
        ReservationEntity savedEntity = reservationRepository.save(entity);
        return entityToDTO(savedEntity);
    }

    public void cancelReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    private void validateReservationInput(ReservationDTO reservationDTO) {
        if (reservationDTO.name().isBlank() || reservationDTO.date().isBlank() || reservationDTO.timeId() == null) {
            throw new InvalidReservationInputException("필수 입력값이 비어있습니다.", reservationDTO.toString());
        }
    }

    private void validateReservationTime(LocalDateTime reservationDateTime) {
        if (reservationDateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidReservationTimeException("현재 시간 이전으로는 예약할 수 없습니다.");
        }

        List<ReservationEntity> existingReservations = reservationRepository.findAll();
        if (existingReservations.stream().anyMatch(r -> r.date().equals(reservationDateTime.toLocalDate().toString()) && r.time().time().equals(reservationDateTime.toLocalTime().toString()))) {
            throw new InvalidReservationTimeException("이미 예약된 시간입니다.");
        }
    }

    private ReservationDTO entityToDTO(ReservationEntity entity) {
        return new ReservationDTO(entity.id(), entity.name(), entity.date(), entity.time().id());
    }
}
