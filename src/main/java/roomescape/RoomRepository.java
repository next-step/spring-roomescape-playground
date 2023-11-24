  package roomescape;

  import java.util.HashMap;
  import java.util.List;
  import java.util.Map;
  import java.util.concurrent.atomic.AtomicLong;

public class RoomRepository {
	private static AtomicLong counter = new AtomicLong();
	private static Map<Long, Room> repository = new HashMap<>();

	public static Long save(Room room) {
		room.setId(counter.incrementAndGet());
		repository.put(room.getId(), room);
		return room.getId();
	}

	public static Room findById(Long id) {
		return repository.get(id);
	}

	public static List<Room> findAll() {
		return repository.values().stream().toList();
	}

	public static void deleteById(Long id) {
		Room result = repository.remove(id);
		if(result == null) throw new NotFoundReservationException("삭제하려는 예약이 존재하지 않습니다.");
	}

	public static void clear() {
		counter.set(0);
		repository.clear();
	}
}
