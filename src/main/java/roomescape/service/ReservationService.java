package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;
import roomescape.domain.Time;
import roomescape.dto.LoginMember;
import roomescape.dto.ReservationRequest;
import roomescape.exception.InvalidDataException;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundDataException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;
    private final ThemeService themeService;

    public ReservationService(ReservationRepository reservationRepository,
            TimeRepository timeRepository,
            ThemeService themeService) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
        this.themeService = themeService;
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation save(ReservationRequest request, LoginMember loginMember) {
        if (request.date().isBefore(LocalDate.now())) {
            throw new InvalidDataException("과거 날짜로 예약할 수 없습니다.");
        }

        Time time = timeRepository.findById(request.timeId())
                                  .orElseThrow(() -> new NotFoundDataException("존재하지 않는 시간입니다."));

        Theme theme = themeService.findById(request.themeId());

        if (reservationRepository.existsDateAndTimeAndTheme(request.date(), time, theme)) {
            throw new InvalidReservationException("해당 시간에 이미 예약이 존재합니다.");
        }

        String reservationName;
        if (request.name() != null && !request.name().isBlank()) {
            reservationName = request.name();
        } else if (loginMember != null) {
            reservationName = loginMember.name();
        } else {
            throw new InvalidDataException("예약자 정보가 필요합니다.");
        }

        Reservation newReservation = new Reservation(null, reservationName, request.date(), time, theme);
        return reservationRepository.save(newReservation);
    }

    public void deleteById(Long id) {
        boolean deleted = reservationRepository.deleteById(id);
        if (!deleted) {
            throw new InvalidReservationException("존재하지 않는 예약입니다.");
        }
    }
}
