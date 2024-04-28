package roomescape;

import java.util.concurrent.atomic.AtomicLong;

public class AutoIncrementId {
    private final AtomicLong nextValue;

    public AutoIncrementId() {
        this.nextValue = new AtomicLong(1);
    }

    public Long getNextId(){
        return nextValue.getAndIncrement();
    }

}
