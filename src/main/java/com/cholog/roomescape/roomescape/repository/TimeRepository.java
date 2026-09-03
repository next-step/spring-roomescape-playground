package com.cholog.roomescape.roomescape.repository;

import com.cholog.roomescape.roomescape.entity.Time;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TimeRepository {

    Time save(Time time);

    List<Time> findAll();

    void delete(Time time);

    Optional<Time> findById(Long timeId);

    Optional<Time> findByTime(LocalTime time);
}
