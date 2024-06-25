package roomescape;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import roomescape.reservation.domain.MemoryReservationRepository;

@Component
@RequiredArgsConstructor
public class PreInit {

    private final MemoryReservationRepository reservationRepository;
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
