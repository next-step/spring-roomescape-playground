package hello;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JdbcTest
public class UpdatingDaoTest {
    private UpdatingDAO updatingDAO;
    private QueryingDAO queryingDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        queryingDAO = new QueryingDAO(jdbcTemplate);
        updatingDAO = new UpdatingDAO(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);
    }

    @Test
    void insert() {
        Customer customer = new Customer("Leonor", "Watling");
        updatingDAO.insert(customer);
        List<Customer> customers = queryingDAO.findCustomerByFirstName("Leonor");
        Assertions.assertThat(customers).hasSize(1);
    }
    @Test
    void delete() {
        int rowNum = updatingDAO.delete(1L);
        Assertions.assertThat(rowNum).isEqualTo(1);
    }
    @Test
    void keyHolder() {
        Customer customer = new Customer("Leonor", "Watling");
        Long id = updatingDAO.insertWithKeyHolder(customer);
        Assertions.assertThat(id).isNotNull();
    }
}