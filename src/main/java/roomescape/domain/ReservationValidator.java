package roomescape.domain;

import static roomescape.exception.ExceptionMessage.*;

import java.lang.reflect.Field;

import roomescape.dto.request.ReservationRequest;
import roomescape.exception.BaseException;

public class ReservationValidator {

    /*public void checkNull(ReservationRequest reservationRequest) {
        for (Field field : reservationRequest.getClass().getDeclaredFields()) {
            if (field.get(this) == null) {
                throw new BaseException(TEST_EXCEPTION);
            }
        }
    }*/
}
