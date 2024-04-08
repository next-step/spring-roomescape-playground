package roomescape.Domain;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reservation {
    private long id;
    private String name;
    private String date;
    private long timeId; // 수정된 부분: Time 객체 대신 time_id를 직접 저장

    public Reservation() {
    }

    public Reservation(long id, String name, String date, long timeId) { // 수정된 부분: Time 객체 대신 time_id를 직접 받음
        this.id = id;
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode reservationJson = objectMapper.createObjectNode();
            reservationJson.put("id", this.id);
            reservationJson.put("name", this.name);
            reservationJson.put("date", this.date);
            reservationJson.put("timeId", this.timeId); // 수정된 부분: Time 객체 대신 time_id를 직접 출력
            return objectMapper.writeValueAsString(reservationJson);
        } catch (JsonProcessingException e) {
            System.err.println("Error converting Reservation object to JSON: " + e.getMessage());
            return "{\"error\": \"Failed to convert object to JSON\"}";
        }
    }
}