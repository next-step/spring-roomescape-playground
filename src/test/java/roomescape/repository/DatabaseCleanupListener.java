package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class DatabaseCleanupListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestMethod(TestContext testContext) {
        JdbcTemplate jdbcTemplate = testContext.getApplicationContext()
                .getBean(JdbcTemplate.class);
        jdbcTemplate.update("TRUNCATE TABLE reservation RESTART IDENTITY");
        jdbcTemplate.update("TRUNCATE TABLE time RESTART IDENTITY");
    }
}
