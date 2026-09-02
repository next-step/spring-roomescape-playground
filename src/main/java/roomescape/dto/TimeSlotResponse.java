package roomescape.dto;

import roomescape.domain.TimeSlot;

public record TimeSlotResponse(
        Long id,
        String time
) {
    public static TimeSlotResponse from(TimeSlot timeSlot) {
        return new TimeSlotResponse(
                timeSlot.getId(),
                timeSlot.getStartAt().toString()
        );
    }
}
