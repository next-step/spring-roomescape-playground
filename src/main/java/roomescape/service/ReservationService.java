package roomescape.service;

import roomescape.domain.Reservation;
import roomescape.domain.TimeFormatter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReservationService {
    public static List<Reservation> initTestDataToreservation(){
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(1, "브라운", LocalDate.of(2023,1,1), LocalTime.of(10,30)));
        reservations.add(new Reservation(2, "브라운", LocalDate.of(2023,1,1), LocalTime.of(10,30)));
        reservations.add(new Reservation(3, "브라운", LocalDate.of(2023,1,1), LocalTime.of(10,30)));
        return reservations;
    }

    public static Reservation makeMapToReservation(int id, Map<String, String> param){
        String name = param.get("name");
        LocalDate date = LocalDate.parse(param.get("date"), TimeFormatter.dateFormatter);
        LocalTime time = LocalTime.parse(param.get("time"), TimeFormatter.timeFormatter);
        return new Reservation(id, name, date, time);
    }
}
