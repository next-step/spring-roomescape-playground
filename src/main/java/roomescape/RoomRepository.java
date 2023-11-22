  package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class RoomRepository {
	private static AtomicLong counter = new AtomicLong(1);
	private static Map<Long, Room> repository = new HashMap<>();

	static {
//		seedData();
	}

	public static Long save(Room room) {
		room.setId(counter.getAndIncrement());
		repository.put(room.getId(), room);
		return room.getId();
	}

	public static Room findById(Long id) {
		return repository.get(id);
	}

	public static List<Room> findAll() {
		return repository.values().stream().toList();
	}

	private static void seedData() {
		Room room1 = new Room("박한수", LocalDate.now(), LocalTime.now());
		Room room2 = new Room("홍길동", LocalDate.now(), LocalTime.now());
		Room room3 = new Room("브라운", LocalDate.now(), LocalTime.now());

		save(room1);
		save(room2);
		save(room3);
	}

	public void deleteById(Long id) {
		Room result = repository.remove(id);
		if(result == null) throw new NotFoundReservationException("삭제하려는 예약이 존재하지 않습니다.");
	}
}
