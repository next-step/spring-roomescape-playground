package roomescape.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import({TimeDatabaseRepository.class})
class TimeDatabaseRepositoryTest {

    @Autowired
    private TimeDatabaseRepository timeDatabaseRepository;



}
