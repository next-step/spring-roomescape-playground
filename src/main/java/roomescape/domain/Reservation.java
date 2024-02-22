package roomescape.domain;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public record Reservation(Long id, @NotBlank String name, @NotBlank String date, @NotBlank String time) {
}
