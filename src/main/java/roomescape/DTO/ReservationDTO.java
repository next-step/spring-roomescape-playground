package roomescape.DTO;

public record ReservationDTO (
        String name,
        String date,
        Long time
){
    public ReservationDTO(String name, String date, Long time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
