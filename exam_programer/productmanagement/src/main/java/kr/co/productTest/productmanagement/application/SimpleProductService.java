package kr.co.productTest.productmanagement.application;

import kr.co.productTest.productmanagement.api.domain.ProductRepository;
import kr.co.productTest.productmanagement.application.request.ProductDto;
import kr.co.productTest.productmanagement.core.feature.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleProductService {

    private final ProductRepository productRepository;
    private final ValidationService validationService;

    @Autowired
    public SimpleProductService(ProductRepository productRepository, ValidationService validationService) {
        this.productRepository = productRepository;
        this.validationService = validationService;
    }

    public ProductDto add(ProductDto productDto) {
        // 1. ProductDto를 Product로 변화하는 코드
        Product product = ProductDto.toEntity(productDto);
        validationService.checkValid(product);

        // 2. 레포지토리를 호출하는 코드
        Product savedProduct = productRepository.add(product);
        // Mock 없이 아래와 같이 진행할때 하지만 any를 사용함으로써 아무 메서드의 인자로 사용가능하다.
//        Product savedProduct = product;
//        savedProduct.setId(1L);

        // 3. Product를 ProductDto로 변환하는 코드
        ProductDto saveProductDto = ProductDto.toDto(savedProduct);
        ProductDto saveProductDto = null;

        return saveProductDto;
    }

    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id);
        ProductDto productDto = ProductDto.toDto(product);
        return productDto;
    }

    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(product -> ProductDto.toDto(product))
                .collect(Collectors.toList());
        return productDtos;
    }

    public List<ProductDto> findByName(String name) {
        List<Product> products = productRepository.findByNameContaining(name);
        List<ProductDto> productDtos = products.stream()
                .map(product -> ProductDto.toDto(product))
                .toList();
        return productDtos;
    }

    public ProductDto update(ProductDto productDto) {
        Product product = ProductDto.toEntity(productDto);
        Product updateProduct = productRepository.update(product);
        // DTO에서 변환된 Product를 수정되기 전의 Product와 바꿔버리면 된다.
        // Product 인스턴스의 값을 바꾸는 것이 아니라 Product 인스턴스 자체를 통채로 바꾸는것이다.
//        ProductDto updateProductDto = modelMapper.map(updateProduct, ProductDto.class);
        ProductDto updateProductDto = ProductDto.toDto(updateProduct);

        return updateProductDto;
    }

    public void delete(Long id) {
//        listProductRepository.delete(id);
        productRepository.delete(id);
    }
}
