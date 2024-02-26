package roomescape.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.ReservationResponseDto;
import roomescape.exception.NoParameterException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationDao reservationDao;

    public List<ReservationResponseDto> loadReservationList(){
        return reservationDao.findAll();
    }

    public ReservationResponseDto createReservation(ReservationRequestDto reservationRequest){
        if (StringUtils.isEmpty(reservationRequest.name())){
            throw new NoParameterException("Reservation Have No Name Parameter");
        } else if (StringUtils.isEmpty(reservationRequest.date())){
            throw new NoParameterException("Reservation Have No Date Parameter");
        } else if (StringUtils.isEmpty(reservationRequest.time())){
            throw new NoParameterException("Reservation Have No Time Parameter");
        }
        return reservationDao.insert(reservationRequest);
    }

    public void deleteReservation(Long id){
        reservationDao.delete(id);
    }
}
