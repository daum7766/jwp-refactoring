package kitchenpos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductTest {

    @Test
    @DisplayName("물품개수와 가격을 곱한 값을 반환한다.")
    void price() {
        //given
        Product product = new Product(1L, "물건1", new BigDecimal(1200L));
        MenuProduct menuProduct = new MenuProduct(product, 3);

        //when
        BigDecimal price = menuProduct.price();

        //then
        assertThat(price).isEqualTo(BigDecimal.valueOf(3600L));
    }
}
