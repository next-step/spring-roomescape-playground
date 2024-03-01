package roomescape.dto;

public record ReservationResponseDTO(
        Long id,
        String name,
        String date,
        String time
) {
}
