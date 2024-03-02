package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.exception.NoParameterException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public List<ReservationResponseDto> loadReservationList(){
        return reservationDao.findAll().stream()
                .map(ReservationResponseDto::toReservationDto)
                .collect(Collectors.toList());
    }

    public ReservationResponseDto createReservation(ReservationRequestDto reservationRequest){
        if (StringUtils.isEmpty(reservationRequest.name())){
            throw new NoParameterException("Reservation Have No Name Parameter");
        } else if (StringUtils.isEmpty(reservationRequest.date())){
            throw new NoParameterException("Reservation Have No Date Parameter");
        }
        else if (reservationRequest.time() == null){
            throw new NoParameterException("Reservation Have No Time Parameter");
        }
        Time time = timeDao.findById(reservationRequest.time());
        Reservation newReservation = new Reservation(null, reservationRequest.name(),
                reservationRequest.date(), time);

        return ReservationResponseDto.toReservationDto(reservationDao.insert(newReservation));
    }

    public void deleteReservation(Long id){
        reservationDao.delete(id);
    }
}
