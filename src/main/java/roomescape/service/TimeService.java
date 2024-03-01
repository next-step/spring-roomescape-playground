package roomescape.service;

import java.util.List;

import roomescape.controller.dto.TimeCreate;
import roomescape.controller.dto.TimeResponse;

public interface TimeService {
    List<TimeResponse> findAll();

    TimeResponse add(TimeCreate request);

    void remove(Long id);
}
