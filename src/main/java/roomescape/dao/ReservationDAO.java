package roomescape.dao;

<<<<<<< HEAD
import java.util.List;
import roomescape.entity.Reservation;

public interface ReservationDAO {

    void save(Reservation reservation);

    List<Reservation> getAll();

    void update(Reservation reservation);

    void delete(long id);

    int count();

    Reservation getById(int id);

}
=======
public interface ReservationDAO {
}
>>>>>>> 87aeab1 (feat : gradle 의존성 추가 및 데이터베이스 설정)
