package roomescape.Repository;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {
// JPA??

}

