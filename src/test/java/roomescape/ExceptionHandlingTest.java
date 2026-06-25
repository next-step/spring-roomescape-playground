package roomescape;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.reservation.controller.ReservationController;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.common.exception.NoSuchElementToDeleteException;
import roomescape.reservation.service.ReservationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ExceptionHandlingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    void noSuchElementToDeleteExceptionCreatesNotFoundResponse() throws Exception {
        willThrow(new NoSuchElementToDeleteException("No such a reservation with id 999"))
                .given(reservationService)
                .deleteReservation(999L);

        mockMvc.perform(delete("/reservations/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("No such a reservation with id 999"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    private static String reservationJson(String name, String date, String time) {
        return "{"
                + "\"name\":\"" + name + "\","
                + "\"date\":\"" + date + "\","
                + "\"time\":\"" + time + "\""
                + "}";
    }
}
