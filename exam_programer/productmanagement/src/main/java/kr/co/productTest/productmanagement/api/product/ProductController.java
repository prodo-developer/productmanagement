package kr.co.productTest.productmanagement.api.product;

import jakarta.validation.Valid;
import kr.co.productTest.productmanagement.application.SimpleProductService;
import kr.co.productTest.productmanagement.application.request.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final SimpleProductService simpleProductService;

    @Autowired
    public ProductController(SimpleProductService simpleProductService) {
        this.simpleProductService = simpleProductService;
    }

    @PostMapping(value = "/products")
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        // product를 생성하고 리스트에 넣는 작업이 필요함.
        return simpleProductService.add(productDto);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ProductDto findById(@PathVariable Long id) {
        return simpleProductService.findById(id);
    }

//    @RequestMapping(value = "/products", method = RequestMethod.GET)
//    public List<ProductDto> findAllProduct() {
//        return simpleProductService.findAll();
//    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductDto> findProducts(@RequestParam(required = false) String name) {
        if(null == name)
            return simpleProductService.findAll();

        return simpleProductService.findByName(name);
    }

    @PutMapping(value = "/products/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        productDto.setId(id);
        return simpleProductService.update(productDto);
    }

    @DeleteMapping(value = "/products/{id}")
    public void updateProduct(@PathVariable Long id) {
        simpleProductService.delete(id);
    }
}
