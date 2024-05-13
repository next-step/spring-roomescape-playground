package roomescape.model;

import lombok.Data;

@Data
public class MemberDTO {
    int id;
    String name;
    String date;
    String time;

    public MemberDTO(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
