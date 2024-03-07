package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.exception.ParamException;
import roomescape.exception.ReservationException;
import roomescape.repository.JdbcTemplateReservationRepository;
import roomescape.repository.JdbcTemplateTimeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final JdbcTemplateReservationRepository jdbcTemplateReservationRepository;
    public final JdbcTemplateTimeRepository jdbcTemplateTimeRepository;

    public List<ReservationResponseDto> getAllReservations() {
        return jdbcTemplateReservationRepository.findAllReservation().stream()
                .map(ReservationResponseDto::from)
                .collect(Collectors.toList());
    }

    public Reservation addReservation(ReservationRequestDto reservationRequestDto) {

        if (StringUtils.isEmpty(reservationRequestDto.name()) ||
            StringUtils.isEmpty(reservationRequestDto.date()) ||
            StringUtils.isEmpty(reservationRequestDto.time())) {
            throw new ParamException(ParamException.Type.MISSING_PARAMETER);
        }

        Time time = jdbcTemplateTimeRepository.findById(reservationRequestDto.time());
        Reservation newReservation = new Reservation(null, reservationRequestDto.name(), reservationRequestDto.date(), time);
        jdbcTemplateReservationRepository.insertReservation(newReservation);

        return newReservation;
    }

    public void deleteReservation(Long id) {
        if(!jdbcTemplateReservationRepository.deleteReservation(id)) {
            throw new ReservationException(ReservationException.Type.NOT_FOUND_RESERVATION);
        }
    }
}
