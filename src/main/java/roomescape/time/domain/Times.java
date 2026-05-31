package roomescape.time.domain;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Repository;
import roomescape.time.repository.TimesRepository;

import java.time.LocalTime;
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

    public boolean has(@Nonnull TimeId id) {
        return timesRepository.has(id);
    }

    public @Nullable TimeId getIdAt(@Nonnull LocalTime time) {
        if (time.getSecond() != 0 || time.getNano() != 0)
            throw new TimeException.IllegalPrecision();

        return timesRepository.getIdAt(time);
    }

    public @Nullable Time getAt(@Nonnull LocalTime time) {
        if (time.getSecond() != 0 || time.getNano() != 0)
            throw new TimeException.IllegalPrecision();

        return timesRepository.getAt(time);
    }

    public @Nonnull Time create(@Nonnull CreateTimeInfo info) {
        Time previous = timesRepository.getAt(info.time());
        if (previous != null) {
            throw new TimeException.DuplicateTime(previous.getId());
        }

        return timesRepository.create(info);
    }

    public void delete(@Nonnull TimeId id) {
        timesRepository.delete(id);
    }
}
