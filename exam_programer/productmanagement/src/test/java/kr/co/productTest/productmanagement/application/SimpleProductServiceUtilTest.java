package kr.co.productTest.productmanagement.application;

import kr.co.productTest.productmanagement.api.domain.ProductRepository;
import kr.co.productTest.productmanagement.application.request.ProductDto;
import kr.co.productTest.productmanagement.core.feature.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SimpleProductServiceUtilTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private SimpleProductService simpleProductService;

    @Test
    @DisplayName("상품 추가 후에는 추가된 상품이 반환되어야 한다.")
    void productAddTest() {
        ProductDto productDto = new ProductDto("연필", 300, 20);
        Long PRODUCT_ID = 1L;

        Product product = ProductDto.toEntity(productDto);
        product.setId(PRODUCT_ID);

        when(productRepository.add(any())).thenReturn(product);

        ProductDto savedProductDto = simpleProductService.add(productDto);

        assertTrue(savedProductDto.getId().equals(PRODUCT_ID));
        assertTrue(savedProductDto.getName().equals(productDto.getName()));
        assertTrue(savedProductDto.getPrice().equals(productDto.getPrice()));
        assertTrue(savedProductDto.getAmount().equals(productDto.getAmount()));

        verify(productRepository).add(any());
    }
}