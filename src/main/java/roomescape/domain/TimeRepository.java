package roomescape.domain;

import java.util.List;

public interface TimeRepository {
    Time save(final Time time);

    List<Time> findAll();

    void delete(final Long id);

    Time findById(final Long id);
}
