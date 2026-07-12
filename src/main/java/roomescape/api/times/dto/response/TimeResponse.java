package roomescape.api.times.dto.response;

public record TimeResponse(
        long id,
        String time
) {

    public static TimeResponse of(final long id, final String time) {
        return new TimeResponse(id, time);
    }
}
