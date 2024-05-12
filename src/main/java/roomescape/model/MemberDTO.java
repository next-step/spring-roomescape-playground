package roomescape.model;

import lombok.Data;

@Data
public class MemberDTO {
    /** application.json
     * "id": 1,
     * "name": "브라운",
     * "date": "2023-01-01",
     * "time": "10:00"
     *
     */
    int id;
    String name;
    String date;
    String time;
}
