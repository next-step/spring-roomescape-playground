package roomescape.time.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import roomescape.time.domain.Time;

public interface TimeRepository extends JpaRepository<Time, Long> {
}
