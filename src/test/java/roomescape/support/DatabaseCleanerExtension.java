package roomescape.support;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

public class DatabaseCleanerExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        DataSource dataSource = getDataSourceFrom(context);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        new DatabaseCleaner(jdbcTemplate).clean();
    }

    private DataSource getDataSourceFrom(ExtensionContext context) {
        return SpringExtension.getApplicationContext(context)
                .getBean(DataSource.class);
    }
}
