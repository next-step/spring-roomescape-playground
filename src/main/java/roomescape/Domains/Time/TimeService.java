package roomescape.Domains.Time;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.exceptions.BadRequestException;

@Service
public class TimeService {

  private final TimeRepository timeRepository;

  public TimeService(final TimeRepository timeRepository) {
    this.timeRepository = timeRepository;
  }

  public List<Time> list() {
    return timeRepository.findAll();
  }

  public Time create(final TimeStoreRequestDto requestDto) {
    return timeRepository.save(new Time(requestDto.time()));
  }

  public void deleteByIdOrElseThrowException(final Long id) {
    Time time = timeRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("존재하지 않는 시간입니다."));
    timeRepository.delete(time);
  }
}
