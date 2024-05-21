package roomescape.model;





import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Exception {

    public static LocalDate validateDateIsNotNull(LocalDate date){
        if(Objects.isNull(date))
            throw new IllegalArgumentException("올바르지 않은 날짜 형식입니다.");
        return date;
    }

    public static LocalTime validateTimeIsNotNull(LocalTime time){
        if(Objects.isNull(time))
            throw new IllegalArgumentException("올바르지 않은 시간 형식입니다.");
        return time;
    }

    public static String validateNameIsNotNull(String name){
        if(Objects.isNull(name))
            throw new IllegalArgumentException("올바르지 않은 이름 형식입니다.");
        return name;
    }
}
