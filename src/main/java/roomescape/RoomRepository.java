  package roomescape;

  import java.util.HashMap;
  import java.util.List;
  import java.util.Map;
  import java.util.concurrent.atomic.AtomicLong;

public class RoomRepository {
	private static final AtomicLong counter = new AtomicLong();
	private static final Map<Long, Room> repository = new HashMap<>();

	public Long save(Room room) {
		room.setId(counter.incrementAndGet());
		repository.put(room.getId(), room);
		return room.getId();
	}

	public Room findById(Long id) {
		return repository.get(id);
	}

	public List<Room> findAll() {
		return repository.values().stream().toList();
	}

	public void deleteById(Long id) {
		Room result = repository.remove(id);
		if(result == null) throw new NotFoundReservationException("삭제하려는 예약이 존재하지 않습니다.");
	}

	public void clear() {
		counter.set(0);
		repository.clear();
	}
}
