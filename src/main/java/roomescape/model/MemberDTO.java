package roomescape.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberDTO {
    int id;

    @NotEmpty
    String name;

    @NotEmpty
    String date;

    @NotEmpty
    String time;

    public MemberDTO(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
