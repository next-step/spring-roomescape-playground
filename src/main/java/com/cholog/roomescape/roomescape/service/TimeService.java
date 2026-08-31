package com.cholog.roomescape.roomescape.service;

import com.cholog.roomescape.roomescape.entity.Time;

import java.time.LocalTime;
import java.util.List;

public interface TimeService {

    List<Time> findAllTime();

    Time createTime(LocalTime time);

    void deleteTime(Long timeId);
}
