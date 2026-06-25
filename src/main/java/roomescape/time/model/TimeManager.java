package roomescape.time.model;

import roomescape.common.exception.AfterCloseException;
import roomescape.common.exception.BeforeOpenException;
import roomescape.common.exception.OverlappingReservationTimeException;

import java.time.LocalTime;
import java.util.List;

public class TimeManager {
    public static final LocalTime OPEN_AT = LocalTime.parse("09:00");
    public static final LocalTime CLOSE_AT = LocalTime.parse("22:00");
    public static final int AVAILABLE_HOURS = 2;

    private final List<Time> timeList;

    public TimeManager(List<Time> timeList) {
        this.timeList = timeList;
    }

    public void validateTime(Time time){
        LocalTime startTime = time.getTime();

        if (startTime.isBefore(OPEN_AT))
            throw new BeforeOpenException("Time should be after " + OPEN_AT.toString());

        if (startTime.isAfter(CLOSE_AT.minusHours(AVAILABLE_HOURS)))
            throw new AfterCloseException("Time should be before " + CLOSE_AT.toString());

        if (isOverlapped(startTime))
            throw new OverlappingReservationTimeException("Time is overlapping");
    }

    private boolean isOverlapped(LocalTime newStartTime) {
        LocalTime newEndTime = newStartTime.plusHours(AVAILABLE_HOURS);

        for (Time time : this.timeList) {
            LocalTime startTime = time.getTime();
            LocalTime endTime = time.getTime().plusHours(AVAILABLE_HOURS);

            if (endTime.isAfter(newStartTime) && startTime.isBefore(newEndTime))
                return true;
        }

        return false;
    }
}