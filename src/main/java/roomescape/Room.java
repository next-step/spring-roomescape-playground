package roomescape;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.util.StringUtils;

public class Room {
	Long id;
	String name;
	LocalDate date;
	LocalTime time;

	public void setId(Long id) {
		this.id = id;
	}

	public Room(String name, LocalDate date, LocalTime time) {
		this.name = name;
		this.date = date;
		this.time = time;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void validates() {
		if (StringUtils.isEmpty(name)) {
			throw new DomainEmptyFieldException("이름은 필수 입력값입니다.");
		}
		if (date == null) {
			throw new DomainEmptyFieldException("날짜는 필수 입력값입니다.");
		}
		if (time == null) {
			throw new DomainEmptyFieldException("시간은 필수 입력값입니다.");
		}
	}
}
