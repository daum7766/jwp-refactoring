package kitchenpos.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.application.ProductService;
import kitchenpos.domain.Product;
import kitchenpos.dto.ProductDto;
import kitchenpos.dto.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/api/products")
    public ResponseEntity<ProductResponse> create(@RequestBody final ProductDto productDto) {
        final Product created = productService.create(productDto);
        final URI uri = URI.create("/api/products/" + created.getId());
        return ResponseEntity.created(uri)
            .body(ProductResponse.valueOf(created));
    }

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponse>> list() {
        List<ProductResponse> responses = productService.list()
            .stream()
            .map(ProductResponse::valueOf)
            .collect(Collectors.toList());

        return ResponseEntity.ok()
            .body(responses);
    }
}
