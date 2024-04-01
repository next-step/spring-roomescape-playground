package roomescape;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Reservation {
    private long id;
    private String name;
    private String date;
    private String time;

    public Reservation() {
    }

    public Reservation(long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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
            reservationJson.put("time", this.time);
            return objectMapper.writeValueAsString(reservationJson);
        } catch (JsonProcessingException e) {
            System.err.println("Error converting Reservation object to JSON: " + e.getMessage());
            return "{\"error\": \"Failed to convert object to JSON\"}";
        }
    }

}