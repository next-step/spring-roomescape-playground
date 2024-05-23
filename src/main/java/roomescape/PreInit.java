package roomescape;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import roomescape.domain.Dto.RequestDto;
import roomescape.domain.Repository.ReservationRepository;

@Component
@RequiredArgsConstructor
public class PreInit {

    private final ReservationRepository reservationRepository;
//    @PostConstruct
//    public void init() {
//        RequestDto requestDto1 = new RequestDto();
//        requestDto1.setName("ju");
//        requestDto1.setTime("asdasd");
//        requestDto1.setDate("asdasd");
//        reservationRepository.save(requestDto1.toReservation());
//
//    }
}
