package roomescape.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberDTO {
    int id;

    @NotBlank
    String name;

    @NotBlank
    String date;

    @NotBlank
    String time;

    public MemberDTO(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
