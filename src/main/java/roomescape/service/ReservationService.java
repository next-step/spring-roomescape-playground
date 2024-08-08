package roomescape.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import roomescape.domain.Reservation;
import roomescape.domain.repository.ReservationRepository;
import roomescape.exception.ReservationNotFoundException;

@Transactional // 트랜잭션 관리
@Service // 해당 클래스를 Bean 으로 인식, 의존성 주입이 가능하다
public class ReservationService {

  private final ReservationRepository reservationRepository;

  public ReservationService(ReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }

  @Transactional(readOnly = true)
  public List<ReservationDto> findAll() {
    return reservationRepository.findAll().stream()
                                .map(ReservationDto::from)
                                .collect(Collectors.toList());
  }

  public ReservationDto save(Reservation reservation) {
    Reservation savedReservation = reservationRepository.save(reservation);
    return ReservationDto.from(savedReservation);
  }

  public void deleteById(Long id) {
    if (!reservationRepository.deleteById(id)) {
      throw new ReservationNotFoundException();
    }
  }
}
