package kitchenpos.application;

import java.util.List;
import kitchenpos.domain.Product;
import kitchenpos.dto.ProductDto;
import kitchenpos.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(final ProductDto productDto) {
        return productRepository.save(productDto.toProduct());
    }

    public List<Product> list() {
        return productRepository.findAll();
    }
}
