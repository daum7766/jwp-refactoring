package kitchenpos.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.List;
import kitchenpos.factory.KitchenPosFactory;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTest {

    @Test
    @DisplayName("price 가 null 이라면 에러가 발생한다.")
    void createExceptionWithPriceNull() {
        //given
        MenuGroup menuGroup = KitchenPosFactory.getStandardMenuGroup();
        List<MenuProduct> menuProducts = KitchenPosFactory.getStandardMenuProducts();

        //when
        ThrowingCallable callable = () -> new Menu.Builder()
            .menuGroup(menuGroup)
            .menuProducts(menuProducts)
            .build();

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -9999})
    @DisplayName("price 가 음수라면 에러가 발생한다.")
    void createExceptionWithPriceLessThenZero(int price) {
        //given
        MenuGroup menuGroup = KitchenPosFactory.getStandardMenuGroup();
        List<MenuProduct> menuProducts = KitchenPosFactory.getStandardMenuProducts();

        //when
        ThrowingCallable callable = () -> new Menu.Builder()
            .menuGroup(menuGroup)
            .menuProducts(menuProducts)
            .price(price)
            .build();

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("메뉴의 가격이 모든 제품의 가격합보다 크다면 에러가 발생한다.")
    void createExceptionWithNotEqualPriceSum() {
        //given
        MenuGroup menuGroup = KitchenPosFactory.getStandardMenuGroup();
        List<MenuProduct> menuProducts = KitchenPosFactory.getStandardMenuProducts();

        //when
        ThrowingCallable callable = () -> new Menu.Builder()
            .menuGroup(menuGroup)
            .menuProducts(menuProducts)
            .price(1100L)
            .build();

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
