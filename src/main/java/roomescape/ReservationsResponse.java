package roomescape;

import java.util.List;

public class ReservationsResponse {

    private final List<ReservationResponse> items;

    public ReservationsResponse(List<ReservationResponse> items) {
        this.items = List.copyOf(items);
    }

    public List<ReservationResponse> getItems() {
        return items;
    }
}
