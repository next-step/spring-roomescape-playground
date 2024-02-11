package roomescape.domain.value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.*;

@AllArgsConstructor
@Getter
public class Date {

    private LocalDate date;

    public Date(String date) {
        this.date = LocalDate.parse(date);
    }
}
