package kr.co.productTest.productmanagement.application;

import kr.co.productTest.productmanagement.api.domain.ProductRepository;
import kr.co.productTest.productmanagement.application.request.ProductDto;
import kr.co.productTest.productmanagement.core.feature.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimpleProductService {

//    private final ListProductRepository listProductRepository;
//    private final DatabaseProductRepository databaseProductRepository;

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationService validationService;

    @Autowired
    public SimpleProductService(ProductRepository productRepository, ModelMapper modelMapper, ValidationService validationService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationService = validationService;
    }

    public ProductDto add(ProductDto productDto) {
        // 1. ProductDto를 Product로 변화하는 코드
        Product product = modelMapper.map(productDto, Product.class);
        validationService.checkValid(product);

        // 2. 레포지토리를 호출하는 코드
//        Product savedProduct = listProductRepository.add(product);
        Product savedProduct = productRepository.add(product);

        // 3. Product를 ProductDto로 변환하는 코드
        ProductDto saveProductDto = modelMapper.map(savedProduct, ProductDto.class);

        return saveProductDto;
    }

    public ProductDto findById(Long id) {
//        Product product = listProductRepository.findById(id);
        Product product = productRepository.findById(id);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return productDto;
//        return new ProductDto();
//      ProductRepository에서 조회하여 ModelMapper로 변환한 올바른 ProductDto가 반환되어야 하는 코드를
//      아무 필드도 초기화 하지 않은 ProductDto를 생성하여 반환하도록 수정한다.
//      각 필드의 값이 다르므로 false로 나옴.
    }

    public List<ProductDto> findAll() {
//        List<Product> products = listProductRepository.findAll();
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
        return productDtos;
    }

    public List<ProductDto> findByName(String name) {
//        List<Product> products = listProductRepository.findByName(name);
        List<Product> products = productRepository.findByNameContaining(name);
        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
        return productDtos;
    }

    public ProductDto update(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
//        Product updateProduct = listProductRepository.update(product);
        Product updateProduct = productRepository.update(product);
        // DTO에서 변환된 Product를 수정되기 전의 Product와 바꿔버리면 된다.
        // Product 인스턴스의 값을 바꾸는 것이 아니라 Product 인스턴스 자체를 통채로 바꾸는것이다.
        ProductDto updateProductDto = modelMapper.map(updateProduct, ProductDto.class);

        return updateProductDto;
    }

    public void delete(Long id) {
//        listProductRepository.delete(id);
        productRepository.delete(id);
    }
}
