package roomescape.Domains.Time;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<Time, Long> {

  Optional<Time> findFirstByTime(final String time);
}
