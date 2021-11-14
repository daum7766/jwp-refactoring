package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import kitchenpos.domain.Product;
import kitchenpos.dto.ProductDto;
import kitchenpos.factory.KitchenPosDtoFactory;
import kitchenpos.factory.KitchenPosFactory;
import kitchenpos.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private final Product standardProduct = KitchenPosFactory.getStandardProduct();
    private final List<Product> standardProducts = KitchenPosFactory.getStandardProducts();

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품을 생성한다.")
    void create() {
        //given
        ProductDto request = KitchenPosDtoFactory.getStandardProduct();

        given(productRepository.save(any())).willReturn(standardProduct);

        Product expected = KitchenPosFactory.getStandardProduct();

        //when
        Product product = productService.create(request);

        //then
        assertThat(product.getId()).isNotZero();
        assertThat(product).usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    @DisplayName("모든 상품을 가져온다.")
    void list() {
        //given
        given(productRepository.findAll()).willReturn(standardProducts);

        //when
        List<Product> products = productService.list();
        //then
        assertThat(products).hasSize(standardProducts.size())
            .isEqualTo(standardProducts);
    }
}
