package roomescape.time.request;

import jakarta.annotation.Nonnull;
import java.time.LocalTime;

public record TimeRequest(@Nonnull Long id, @Nonnull LocalTime time) {

}
