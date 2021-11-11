package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.dao.ProductDao;
import kitchenpos.dto.ProductDto;
import kitchenpos.factory.KitchenPosFactory;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductDtoServiceTest {

    private final ProductDto standardProductDto = KitchenPosFactory.getStandardProduct();
    private final List<ProductDto> standardProductDtos = KitchenPosFactory.getStandardProducts();

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductService productService;

    @BeforeAll
    static void beforeSetup() {

    }

    @Test
    @DisplayName("상품을 생성한다.")
    void create() {
        //given
        ProductDto request = new ProductDto();
        request.setName(standardProductDto.getName());
        request.setPrice(standardProductDto.getPrice());
        given(productDao.save(request)).willReturn(standardProductDto);

        //when
        ProductDto productDto = productService.create(request);

        //then
        assertThat(productDto.getId()).isNotZero();
        assertThat(productDto).usingRecursiveComparison()
            .isEqualTo(standardProductDto);
    }

    @Test
    @DisplayName("가격이 null 이라면 에러가 발생한다.")
    void createWithPriceNull() {
        //given
        ProductDto request = new ProductDto();
        request.setName(standardProductDto.getName());

        //when
        ThrowingCallable callable = () -> productService.create(request);

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100})
    @DisplayName("가격이 음수라면 에러가 발생한다.")
    void createWithMinusPrice(int price) {
        //given
        ProductDto request = new ProductDto();
        request.setName(standardProductDto.getName());
        request.setPrice(new BigDecimal(price));

        //when
        ThrowingCallable callable = () -> productService.create(request);

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("모든 상품을 가져온다.")
    void list() {
        //given
        given(productDao.findAll()).willReturn(standardProductDtos);
        //when
        List<ProductDto> productDtos = productService.list();
        //then
        assertThat(productDtos).hasSize(standardProductDtos.size())
            .containsExactly(standardProductDto);
    }
}
