package kr.co.productTest.productmanagement.api.domain;

import kr.co.productTest.productmanagement.core.feature.Product;

import java.util.List;

/**
 * 인터페이스에 의존하기
 */
public interface ProductRepository {

    Product add(Product product);
    Product findById(Long id);
    List<Product> findAll();
    List<Product> findByNameContaining(String name);
    Product update(Product product);
    void delete(Long id);
}
