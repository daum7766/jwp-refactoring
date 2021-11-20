package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import kitchenpos.domain.Menu;
import kitchenpos.dto.MenuDto;
import kitchenpos.factory.KitchenPosDtoFactory;
import kitchenpos.factory.KitchenPosFactory;
import kitchenpos.repository.MenuGroupRepository;
import kitchenpos.repository.MenuProductRepository;
import kitchenpos.repository.MenuRepository;
import kitchenpos.repository.ProductRepository;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    private final Menu standardMenu = KitchenPosFactory.getStandardMenu();
    private final List<Menu> standardMenus = KitchenPosFactory.getStandardMenus();

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @Mock
    private MenuProductRepository menuProductRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private MenuService menuService;

    @Test
    @DisplayName("메뉴를 생성한다.")
    void create() {
        //given
        MenuDto request = KitchenPosDtoFactory.getStandardMenu();

        given(menuGroupRepository.findById(1L))
            .willReturn(Optional.of(KitchenPosFactory.getStandardMenuGroup()));
        given(productRepository.findById(1L))
            .willReturn(Optional.of(KitchenPosFactory.getStandardProduct()));
        given(menuProductRepository.save(any()))
            .willReturn(KitchenPosFactory.getStandardMenuProduct());
        given(menuRepository.save(any())).willReturn(standardMenu);

        //when
        Menu menu = menuService.create(request);

        //then
        assertThat(menu).usingRecursiveComparison()
            .isEqualTo(standardMenu);
    }

    @Test
    @DisplayName("존재하지 않는 MenuGroup 이면 에러가 발생한다.")
    void createExceptionWithNotExistMenuGroup() {
        //given
        MenuDto request = KitchenPosDtoFactory.getStandardMenu();
        request.setMenuGroupId(2L);

        //when
        ThrowingCallable callable = () -> menuService.create(request);

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("모든 메뉴를 가져온다")
    void list() {
        //given
        given(menuRepository.findAll()).willReturn(standardMenus);

        //when
        List<Menu> menus = menuService.list();

        //then
        assertThat(menus).hasSize(standardMenus.size())
            .usingRecursiveComparison()
            .isEqualTo(standardMenus);
    }
}
