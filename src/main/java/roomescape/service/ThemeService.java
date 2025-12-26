package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Theme;
import roomescape.exception.NotFoundDataException;
import roomescape.repository.ThemeRepository;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme findById(Long id) {
        return themeRepository.findById(id)
                              .orElseThrow(() -> new NotFoundDataException("존재하지 않는 테마입니다."));
    }
}
