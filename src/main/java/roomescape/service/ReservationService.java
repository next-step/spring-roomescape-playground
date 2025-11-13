package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong; // 1. ID 생성을 위해 추가

@Service // 2. @Service를 클래스 위에 붙입니다!
public class ReservationService {

    // 3. 서비스가 내부에 데이터 목록을 '소유'하고 관리합니다. (final로 불변성)
    private final List<Reservation> reservations = new ArrayList<>();

    // 4. DB의 Auto-Increment 대신 사용할 ID 카운터 (AtomicLong은 동시성 보장)
    private final AtomicLong counter = new AtomicLong();

    // 5. 생성자: 이 Service 클래스의 '객체'가 처음 생성될 때 호출됩니다.
    //    (DB가 없으니) 여기서 초기 데이터를 세팅합니다.
    public ReservationService() {
        // "setReservations" 메서드 대신 생성자에서 초기 데이터를 직접 추가합니다.
        reservations.add(new Reservation(counter.incrementAndGet(), "브라운", "2025-01-01", "10:00"));
        reservations.add(new Reservation(counter.incrementAndGet(), "코니", "2025-01-02", "11:00"));
    }

    // --- 이제부터 이 Service가 제공할 기능(메서드)들 ---


    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public Reservation addReservation(Reservation newReservation) {
        Reservation savedReservation = new Reservation(
                counter.incrementAndGet(), // 새 ID 발급
                newReservation.getName(),
                newReservation.getDate(),
                newReservation.getTime()
        );
        reservations.add(savedReservation);
        return savedReservation;
    }

    public boolean deleteReservation(Long id) {
        // 리스트에서 ID가 일치하는 예약을 찾아서 제거합니다.
        return reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    public void clear() {
        reservations.clear(); // 리스트를 비웁니다.
        counter.set(0L);      // 카운터를 0으로 리셋합니다.
    }
}
