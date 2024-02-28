package kr.co.productTest.productmanagement.application.request;

import jakarta.validation.constraints.NotNull;
import kr.co.productTest.productmanagement.core.feature.Product;

import java.util.Objects;

public class ProductDto {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private Integer amount;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public ProductDto() {
    }

    public ProductDto(String name, Integer price, Integer amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public ProductDto(Long id, String name, Integer price, Integer amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    /**
     * 4가지 필드를 setter로 넣어줌
     * static 메서드가 아닌 인스턴스 메서드로 동일한 기능을 수행
     * @param productDto
     * @return
     */
    public static Product toEntity(ProductDto productDto) {
        Product product = new Product(
            productDto.getId(),
            productDto.getName(),
            productDto.getPrice(),
            productDto.getAmount()
        );

        return product;
    }

    /**
     * id를 제외한 세가지 필드로 생성자로 생성
     * @param product
     * @return
     */
    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getAmount()
        );

        productDto.setId(product.getId());

        return productDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, amount);
    }
}
