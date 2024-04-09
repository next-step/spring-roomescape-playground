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
    private Time time;


    public Reservation(long id, String name, String date, Time time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }


    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode reservationJson = objectMapper.createObjectNode();
            reservationJson.put("id", this.id);
            reservationJson.put("name", this.name);
            reservationJson.put("date", this.date);
            // time 객체 Json으로 변환
            String timeJson = objectMapper.writeValueAsString(this.time);
            reservationJson.put("time", timeJson);
            return objectMapper.writeValueAsString(reservationJson);
        } catch (JsonProcessingException e) {
            System.err.println("Error converting Reservation object to JSON: " + e.getMessage());
            return "{\"error\": \"Failed to convert object to JSON\"}";
        }
    }

}