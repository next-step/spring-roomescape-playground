package roomescape.Domains.Reservation;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.Domains.Time.Time;
import roomescape.Domains.Time.TimeRepository;
import roomescape.exceptions.BadRequestException;

@Service
public class ReservationService {

  private final TimeRepository timeRepository;
  private final ReservationRepository reservationRepository;

  public ReservationService(
      final TimeRepository timeRepository,
      final ReservationRepository reservationRepository) {
    this.timeRepository = timeRepository;
    this.reservationRepository = reservationRepository;
  }

  public Reservation create(ReservationStoreRequestDto requestDto) {
    Time time = timeRepository.findFirstByTime(requestDto.time())
        .orElseThrow(() -> new BadRequestException("존재하지 않는 시간입니다."));

    return reservationRepository.save(
        new Reservation(requestDto.name(), requestDto.date(), time));
  }

  public List<Reservation> list() {
    return this.reservationRepository.findAll();
  }

  public void deleteByIdOrElseThrowException(Long id) {
    Reservation reservation = this.reservationRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("존재하지 않는 예약입니다."));
    this.reservationRepository.delete(reservation);
  }
}
