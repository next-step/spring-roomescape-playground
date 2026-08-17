package cholog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class QueryingDaoTest {

    private QueryingDAO queryingDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        queryingDAO = new QueryingDAO(jdbcTemplate);

        jdbcTemplate.execute("drop table customers if exists");
        jdbcTemplate.execute("create table customers(" +
                "id SERIAL, first_name varchar(255), last_name varchar(255)) ");

        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);
    }

    @Test
    void count() {
        // given
        int expect = 4;

        // when
        int actual = queryingDAO.count();

        // then
        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void getLastName() {
        // given
        String expect = "Woo";

        // when
        String actualLastName = queryingDAO.getLastName(1L);

        assertThat(actualLastName).isEqualTo(expect);
    }

    @Test
    void findCustomerById() {
        // when
        Customer customer = queryingDAO.findCustomerById(1L);

        // then
        assertThat(customer).isNotNull();
        assertThat(customer.getLastName()).isEqualTo("Woo");
    }

    @Test
    void findAllCustomers() {
        // given
        int expect = 4;

        // when
        List<Customer> actualCustomerCount = queryingDAO.findAllCustomers();

        // then
        assertThat(actualCustomerCount).hasSize(expect);
    }

    @Test
    void findCustomerByFirstName() {
        // given
        int expect = 2;

        // when
        List<Customer> customers = queryingDAO.findCustomerByFirstName("Josh");

        // then
        assertThat(customers).hasSize(expect);
    }
}
