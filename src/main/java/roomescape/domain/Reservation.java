package roomescape.domain;


import jakarta.validation.constraints.NotBlank;

@NotBlank
public record Reservation(Long id, String name, String date, Time time) {
}