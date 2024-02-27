package kr.co.productTest.productmanagement.repository;

import kr.co.productTest.productmanagement.api.domain.ProductRepository;
import kr.co.productTest.productmanagement.core.aop.endpoint.exception.EntityValidationException;
import kr.co.productTest.productmanagement.core.feature.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 서비스 환경
 * 인터페이스에 의존하도록 코드 변경 -> implements ProductRepository
 */
@Repository
@Profile("prod")
public class DatabaseProductRepository implements ProductRepository {

//    private JdbcTemplate jdbcTemplate;

//    @Autowired
//    public DatabaseProductRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
    // jdbcTemplate -> namedParameterJdbcTemplate : SQL 쿼리를 보낼 때 물음표로 매개변수를 매핑하지 않고 매개변수의 이름을 통해
    // SQL 쿼리와 값을 매핑한다. 이를 통해 매개변수 순서가 바뀌거나 수가 많아도 헷갈리지 않는다.
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DatabaseProductRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }



    public Product add(Product product) {
//        jdbcTemplate
//                .update("INSERT INTO products (name, price, amount) VALUES (?, ?, ?)",
//                product.getName(), product.getPrice(), product.getAmount());

        KeyHolder keyHolder = new GeneratedKeyHolder(); // id 채워주기
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(product);

        namedParameterJdbcTemplate.update("INSERT INTO products (name, price, amount) VALUES (:name, :price, :amount)", namedParameter, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        product.setId(generatedId);

        return product;
    }

    public Product findById(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);

        Product product = null;

        try {
            product = namedParameterJdbcTemplate.queryForObject(
                    "SELECT id, name, price, amount FROM products WHERE id=:id",
                    namedParameter,
                    new BeanPropertyRowMapper<>(Product.class)
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityValidationException("Product를 찾지 못했습니다.");
        }
        return product;
    }

    public List<Product> findAll() {
        List<Product> products = namedParameterJdbcTemplate.query(
                "SELECT * FROM products",
                new BeanPropertyRowMapper<>(Product.class)
        );

        return products;
    }

    public List<Product> findByNameContaining(String name) {
//    public List<Product> findByName(String name) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("name", "%" + name + "%");

        List<Product> products = namedParameterJdbcTemplate.query(
                "SELECT * FROM products WHERE name LIKE :name",
                namedParameter,
                new BeanPropertyRowMapper<>(Product.class)
        );

        return products;
    }

    public Product update(Product product) {
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(product);
        // BeanPropertySqlParameterSources는 getter를 통해 가져옵니다.
        namedParameterJdbcTemplate.update(
                "UPDATE products SET name=:name, price=:price, amount=:amount WHERE id=:id",
                namedParameter
        );

        return product;
    }

    public void delete(Long id) {
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);

        namedParameterJdbcTemplate.update(
                "DELETE FROM products WHERE id=:id",
                namedParameter
        );
    }

}
