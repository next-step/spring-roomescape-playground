package roomescape.reservation.dto;

public class ReservationCreateResponse {
    
    private Long id;

    public ReservationCreateResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
