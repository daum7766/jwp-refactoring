package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import kitchenpos.dao.MenuDao;
import kitchenpos.dao.MenuGroupDao;
import kitchenpos.dao.MenuProductDao;
import kitchenpos.dao.ProductDao;
import kitchenpos.dto.MenuDto;
import kitchenpos.dto.ProductDto;
import kitchenpos.factory.KitchenPosFactory;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuDtoServiceTest {

    private final MenuDto standardMenuDto = KitchenPosFactory.getStandardMenu();
    private final List<MenuDto> standardMenuDtos = KitchenPosFactory.getStandardMenus();

    @Mock
    private MenuDao menuDao;

    @Mock
    private MenuGroupDao menuGroupDao;

    @Mock
    private MenuProductDao menuProductDao;

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private MenuService menuService;

    @Test
    @DisplayName("메뉴를 생성한다.")
    void create() {
        //given
        given(menuGroupDao.existsById(1L)).willReturn(true);
        given(productDao.findById(1L))
            .willReturn(Optional.of(KitchenPosFactory.getStandardProduct()));
        given(menuDao.save(standardMenuDto)).willReturn(standardMenuDto);
        given(menuProductDao.save(any())).willReturn(KitchenPosFactory.getStandardMenuProduct());

        //when
        MenuDto menuDto = menuService.create(standardMenuDto);

        //then
        assertThat(menuDto).usingRecursiveComparison()
            .isEqualTo(standardMenuDto);
    }

    @Test
    @DisplayName("price 가 null 이라면 에러가 발생한다.")
    void createExceptionWithPriceNull() {
        //given
        MenuDto request = KitchenPosFactory.getStandardMenu();
        request.setPrice(null);

        //when
        ThrowingCallable callable = () -> menuService.create(request);

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -9999})
    @DisplayName("price 가 음수라면 에러가 발생한다.")
    void createExceptionWithPriceLessThenZero(int price) {
        //given
        MenuDto request = KitchenPosFactory.getStandardMenu();
        request.setPrice(new BigDecimal(price));

        //when
        ThrowingCallable callable = () -> menuService.create(request);

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 MenuGroup 이면 에러가 발생한다.")
    void createExceptionWithNotExistMenuGroup() {
        //given
        MenuDto request = KitchenPosFactory.getStandardMenu();
        request.setMenuGroupId(2L);
        given(menuGroupDao.existsById(2L)).willReturn(false);

        //when
        ThrowingCallable callable = () -> menuService.create(request);

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴의 가격이 모든 제품의 가격합보다 크다면 에러가 발생한다.")
    void createExceptionWithNotEqualPriceSum() {
        //given
        MenuDto request = KitchenPosFactory.getStandardMenu();
        given(menuGroupDao.existsById(request.getMenuGroupId())).willReturn(true);
        ProductDto productDto = KitchenPosFactory.getStandardProduct();
        productDto.setPrice(new BigDecimal(800));
        given(productDao.findById(1L)).willReturn(Optional.of(productDto));

        //when
        ThrowingCallable callable = () -> menuService.create(request);

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("모든 메뉴를 가져온다")
    void list() {
        //given
        given(menuDao.findAll()).willReturn(standardMenuDtos);
        given(menuProductDao.findAllByMenuId(any()))
            .willReturn(KitchenPosFactory.getStandardMenuProducts());

        //when
        List<MenuDto> menuDtos = menuService.list();

        //then
        assertThat(menuDtos).hasSize(standardMenuDtos.size())
            .usingRecursiveComparison()
            .isEqualTo(KitchenPosFactory.getStandardMenus());
    }
}