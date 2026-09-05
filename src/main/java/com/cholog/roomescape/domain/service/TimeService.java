package com.cholog.roomescape.domain.service;

import com.cholog.roomescape.domain.entity.Time;

import java.time.LocalTime;
import java.util.List;

public interface TimeService {

    List<Time> findAllTime();

    Time createTime(LocalTime time);

    void deleteTime(Long timeId);
}
