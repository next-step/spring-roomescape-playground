package roomescape.time.domain;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Repository;
import roomescape.time.repository.TimesRepository;

import java.util.List;

@Repository
public class Times {
    private final TimesRepository timesRepository;

    public Times(TimesRepository timesRepository) {
        this.timesRepository = timesRepository;
    }

    public @Nonnull List<Time> getAll() {
        return timesRepository.getAll();
    }

    public @Nonnull Time create(@Nonnull CreateTimeInfo info) {
        Time previous = timesRepository.getByTime(info.time());
        if (previous != null) {
            // 데이터베이스를 거치지 않고 검사하기 위해, schema의 UNIQUE 제약이 있는데도 중복 작성
            // 실질적인 정합성 보장은 DB 측에서 진행
            throw new TimeException.DuplicateTime(previous.getId());
        }

        return timesRepository.create(info);
    }

    public void delete(@Nonnull TimeId id) {
        timesRepository.delete(id);
    }
}
