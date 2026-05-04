package roomescape.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import roomescape.common.PagePath;

@Controller
public class ReservationPage {
    @GetMapping(PagePath.HOME_PAGE_PATH)
    public String reservationPage() {
        return ViewNames.RESERVATION.getViewName();
    }
}
