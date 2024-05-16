package roomescape.domain;

public record Reservation(
        Long id,
        String name,
        String date,
        String time
) {
}
