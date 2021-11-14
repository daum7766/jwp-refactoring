package kitchenpos.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.math.BigDecimal;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

    @Test
    @DisplayName("가격이 null 이라면 에러가 발생한다.")
    void createWithPriceNull() {
        //given

        //when
        ThrowingCallable callable = () -> new Product(1L, "테스트", null);

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100})
    @DisplayName("가격이 음수라면 에러가 발생한다.")
    void createWithMinusPrice(int price) {
        //given

        //when
        ThrowingCallable callable = () -> new Product(1L, "테스트", new BigDecimal(price));

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
