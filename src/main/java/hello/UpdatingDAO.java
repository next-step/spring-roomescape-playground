package hello;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class UpdatingDAO {
    private JdbcTemplate jdbcTemplate;

    public UpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void insert(Customer customer) {
        String sql = "insert into customers (first_name, last_name) values (?, ?)";
        jdbcTemplate.update(sql, customer.getFirstName(), customer.getLastName());
    }
    public int delete(Long id) {
        String sql = "delete from customers where id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }
    public Long insertWithKeyHolder(Customer customer) {
        String sql = "insert into customers (first_name, last_name) values (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into customers (first_name, last_name) values (?, ?)",
                    new String[]{"id"});
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            return ps;
        }, keyHolder);
        Long id = keyHolder.getKey().longValue();
        return keyHolder.getKey().longValue();
    }
}

