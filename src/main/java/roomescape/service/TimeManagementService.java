package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Time;
import roomescape.dto.TimeCreateForm;
import roomescape.dto.TimeResponseForm;
import roomescape.repository.TimeManagementRepository;

import java.util.List;

@Service
public class TimeManagementService {

    private final TimeManagementRepository timeManagementRepository;

    public TimeManagementService(TimeManagementRepository timeManagementRepository) {
        this.timeManagementRepository = timeManagementRepository;
    }

    public Long create(TimeCreateForm newTimeForm) {
        Time newTime = newTimeForm.toEntity();

        return timeManagementRepository.save(newTime);
    }

    public List<TimeResponseForm> getTimes() {
        List<Time> founds = timeManagementRepository.findAll();

        return founds.stream().map(TimeResponseForm::new).toList();
    }

    public TimeResponseForm getTime(Long newId) {
        Time found = timeManagementRepository.findById(newId);

        return new TimeResponseForm(found);
    }

    public void deleteTime(Long id) {
        timeManagementRepository.deleteById(id);}
}
