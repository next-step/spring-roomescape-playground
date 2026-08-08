package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class TimeController {

    private final List<Time> times = new ArrayList<>();
    private final AtomicLong index = new AtomicLong(1);

    @PostMapping("/times")
    public ResponseEntity<Void> create (@RequestBody Time time) {
        Time newtime = Time.toEntity(time, index.getAndIncrement());
        times.add(newtime);
        return ResponseEntity.created(URI.create("/times/" + newtime.getId())).build();
    }

    @GetMapping("/times")
    public ResponseEntity<List<Time>> read() {
        return ResponseEntity.ok(times);
    }

    @PutMapping("/times/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Time newTime) {
        Time time = times.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        time.update(newTime);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Time time = times.stream()
                .filter(it -> Objects.equals(it.getId(),  id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        times.remove(time);

        return ResponseEntity.noContent().build();
    }
}
