package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roomescape.domain.Theme;
import roomescape.service.ThemeService;

import java.net.URI;
import java.util.List;

@Controller
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping("/themes")
    @ResponseBody
    public List<Theme> getThemes() {
        return themeService.findAll();
    }

    @PostMapping("/themes")
    @ResponseBody
    public ResponseEntity<Theme> createTheme(@RequestBody Theme theme) {
        Theme savedTheme = themeService.save(theme);
        return ResponseEntity
                .created(URI.create("/themes/" + savedTheme.getId()))
                .body(savedTheme);
    }

    @DeleteMapping("/themes/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
