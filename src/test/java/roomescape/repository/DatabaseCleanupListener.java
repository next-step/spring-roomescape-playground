package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class DatabaseCleanupListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestMethod(TestContext testContext) {
        JdbcTemplate jdbcTemplate = testContext.getApplicationContext()
                .getBean(JdbcTemplate.class);
        //jdbcTemplate.update("TRUNCATE TABLE reservation RESTART IDENTITY");
        //jdbcTemplate.update("TRUNCATE TABLE time RESTART IDENTITY");
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM time");
        jdbcTemplate.update("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE time ALTER COLUMN id RESTART WITH 1");
    }
}
