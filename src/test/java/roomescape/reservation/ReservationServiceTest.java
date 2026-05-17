package roomescape.reservation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.global.DatabaseInitialization;
import roomescape.reservation.dao.ReservationsDao;
import roomescape.reservation.domain.ReservationId;
import roomescape.reservation.domain.Reservations;
import roomescape.reservation.dto.CreateReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
public class ReservationServiceTest {
	private final JdbcTemplate jdbcTemplate;
	
	ReservationService service;
	
	@Autowired
	public ReservationServiceTest(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		
		DatabaseInitialization databaseInitialization = new DatabaseInitialization(jdbcTemplate);
		databaseInitialization.initializeTables();
		
		Reservations reservations = new Reservations(new ReservationsDao(jdbcTemplate));
		this.service = new ReservationService(reservations);
	}
	
	@Test
	void 예약_생성_가능() {
		assertThatCode(() -> {
			CreateReservationRequest request = new CreateReservationRequest(
					"pony",
					LocalDate.of(2027, 3, 20),
					LocalTime.of(10, 11)
			);
			
			ReservationResponse response = service.createReservation(request);
			assertThat(response.name()).isEqualTo("pony");
		}).doesNotThrowAnyException();
	}
	
	@Test
	void 예약_조회_가능() {
		CreateReservationRequest request1 = new CreateReservationRequest(
				"이현우",
				LocalDate.of(2011, 3, 20),
				LocalTime.of(10, 11)
		);
		
		CreateReservationRequest request2 = new CreateReservationRequest(
				"우끾끾",
				LocalDate.of(2011, 4, 21),
				LocalTime.of(23, 50)
		);
		
		service.createReservation(request1);
		service.createReservation(request2);
		
		List<ReservationResponse> reservations = service.getReservations();
		Integer currentSize = jdbcTemplate.queryForObject("SELECT count(*) FROM reservations", Integer.class);
		
		assertThat(reservations)
				.hasSize(Objects.requireNonNull(currentSize))
				.anyMatch(response -> response.name().equals(request1.name()))
				.anyMatch(response -> response.name().equals(request2.name()));
	}
	
	@Test
	void 예약_삭제_가능() {
		CreateReservationRequest request = new CreateReservationRequest(
				"이현우",
				LocalDate.of(2087, 3, 20),
				LocalTime.of(10, 11)
		);
		service.createReservation(request);
		
		assertThatCode(() -> service.deleteReservation(new ReservationId(1)))
				.doesNotThrowAnyException();
		
		assertThat(service.getReservations())
				.hasSize(0);
	}
	
	@Test
	void 이름에는_글자_이외_불가능() {
		assertThatThrownBy(() -> {
			CreateReservationRequest request = new CreateReservationRequest(
					"d a", // space
					LocalDate.of(2037, 3, 20),
					LocalTime.of(10, 11)
			);
			service.createReservation(request);
		})
				.isInstanceOf(ReservationInputFormatException.class)
				.extracting("field")
				.isEqualTo("name");
		
		assertThatThrownBy(() -> {
			CreateReservationRequest request = new CreateReservationRequest(
					"나는이름이길어서슬픈생물", // length
					LocalDate.of(2037, 3, 20),
					LocalTime.of(10, 11)
			);
			service.createReservation(request);
		})
				.isInstanceOf(ReservationInputFormatException.class)
				.extracting("field")
				.isEqualTo("name");
	}
	
	@Test
	void 동일_시간에_중복_예약_불가능() {
		CreateReservationRequest request = new CreateReservationRequest(
				"이현우",
				LocalDate.of(2047, 3, 20),
				LocalTime.of(10, 11)
		);
		service.createReservation(request);
		
		assertThatThrownBy(() -> service.createReservation(request))
				.isInstanceOf(ReservationDuplicateTimeException.class);
	}
	
	@Test
	void 존재하지_않는_예약_삭제_불가능() {
		assertThatThrownBy(() -> service.deleteReservation(new ReservationId(1)))
				.isInstanceOf(ReservationDoesNotExistException.class);
	}
}
