package kr.co.productTest.productmanagement.application;

import kr.co.productTest.productmanagement.application.request.ProductDto;
import kr.co.productTest.productmanagement.core.aop.endpoint.exception.EntityValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("prod")
class SimpleProductServiceTest {

    @Autowired
    SimpleProductService simpleProductService;

    @Transactional
    @Test
    @DisplayName("상품을 추가한 후 id로 조회하면 해당 상품이 조회되어야 한다.")
    void productAddAndFindByIdTest() {
        ProductDto productDto = new ProductDto("오렌지", 30000, 200);

        ProductDto savedProductDto = simpleProductService.add(productDto);
        Long savedProductDtoId = savedProductDto.getId();

        ProductDto foundProductDto = simpleProductService.findById(savedProductDtoId);
//        System.out.println(savedProductDto + "==" + foundProductDto);

        assertTrue(savedProductDto.getId() == foundProductDto.getId());
        assertTrue(savedProductDto.getName().equals(foundProductDto.getName()));
        assertTrue(savedProductDto.getPrice().equals(foundProductDto.getPrice()));
        assertTrue(savedProductDto.getAmount().equals(foundProductDto.getAmount()));
        assertTrue(savedProductDto.equals(foundProductDto));
    }

    @Test
    @DisplayName("존재하지 않는 상품id로 조회하면 EntityNotFoundException이 발생해야한다.")
    void findProductNotExistIdTest() {
        Long notExistId = -1L;

        assertThrows(EntityValidationException.class, () -> {
           simpleProductService.findById(notExistId);
        });
    }
}