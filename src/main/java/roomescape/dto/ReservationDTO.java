package roomescape.dto;

public record ReservationDTO (
    String name,
    String date,
    String time
){
    public ReservationDTO(String name, String date, String time){
        this.name = name;
        this.date = date;
        this.time = time;

        if(name.isEmpty() || date.isEmpty() || time.isEmpty()){
            throw new IllegalArgumentException("필요한 인자가 부족합니다!");
        }
    }
}
