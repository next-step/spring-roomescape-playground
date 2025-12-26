package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.exception.NotFoundDataException;
import roomescape.repository.ThemeRepository;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public Theme findById(Long id) {
        return themeRepository.findById(id)
                              .orElseThrow(() -> new NotFoundDataException("존재하지 않는 테마입니다."));
    }

    public Theme save(Theme theme) {
        return themeRepository.save(theme);
    }

    public void deleteById(Long id) {
        boolean deleted = themeRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundDataException("존재하지 않는 테마입니다.");
        }
    }
}
