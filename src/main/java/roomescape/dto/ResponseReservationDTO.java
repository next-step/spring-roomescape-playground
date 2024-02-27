package roomescape.dto;

public record ResponseReservationDTO(
        Long id,
        String name,
        String date,
        String time
) {
}
