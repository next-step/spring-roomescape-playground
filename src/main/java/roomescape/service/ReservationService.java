package roomescape.service;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    public static List<Reservation> initTestDataToreservation(){
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(1, "브라운", LocalDate.of(2023,1,1), LocalTime.of(10,30)));
        reservations.add(new Reservation(2, "브라운", LocalDate.of(2023,1,1), LocalTime.of(10,30)));
        reservations.add(new Reservation(3, "브라운", LocalDate.of(2023,1,1), LocalTime.of(10,30)));
        return reservations;
    }
}
