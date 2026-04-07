package roomescape.Manager;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IdempotencyManager {
    private final Map<String, Object> cache = new ConcurrentHashMap<>(); // 예시로 Map 사용

    public boolean isProcessed(String key) {
        return cache.containsKey(key);
    }

    public Object getResponse(String key) {
        return cache.get(key);
    }

    public void saveResponse(String key, Object response) {
        cache.put(key, response);
    }
}