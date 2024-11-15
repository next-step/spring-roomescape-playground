package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.hello.Customer;

import java.util.List;

@RestController
public class CustomerController {
    private JdbcTemplate jdbcTemplate;

    public CustomerController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/customers")
    public ResponseEntity<Void> save(@RequestBody Customer customer) {
        String sql = "INSERT INTO customers(first_name, last_name) VALUES (?,?)";
        jdbcTemplate.update(sql, customer.getFirstName(), customer.getLastName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/customers") // -> customers 라는 파일(경로)을 만든 적이 없는데 코드가 돌아가긴 함..
    public ResponseEntity<List<Customer>> list() {
        String sql = "select id, first_name, last_name from customers";
        List<Customer> customers = jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {
                    Customer customer = new Customer(
                            resultSet.getLong("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name")
                    );
                    return customer;
                });
        return ResponseEntity.ok().body(customers);
    }
}
