package roomescape.domain;


import jakarta.validation.constraints.NotBlank;

public record Reservation(Long id, @NotBlank String name, @NotBlank String date, @NotBlank String time) {
}
