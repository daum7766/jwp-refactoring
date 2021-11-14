package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import kitchenpos.domain.MenuGroup;
import kitchenpos.dto.MenuGroupDto;
import kitchenpos.factory.KitchenPosDtoFactory;
import kitchenpos.factory.KitchenPosFactory;
import kitchenpos.repository.MenuGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuGroupServiceTest {

    private final MenuGroup standardMenuGroup = KitchenPosFactory.getStandardMenuGroup();
    private final List<MenuGroup> standardMenuGroups = KitchenPosFactory.getStandardMenuGroups();

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @InjectMocks
    private MenuGroupService menuGroupService;

    @Test
    @DisplayName("그룹 생성 테스트")
    void create() {
        //given
        MenuGroupDto request = KitchenPosDtoFactory.getStandardMenuGroup();

        given(menuGroupRepository.save(any())).willReturn(standardMenuGroup);

        //when
        MenuGroup menuGroup = menuGroupService.create(request);

        //then
        assertThat(menuGroup).isNotNull()
            .usingRecursiveComparison()
            .isEqualTo(standardMenuGroup);
        assertThat(menuGroup.getId()).isNotZero();
    }

    @Test
    @DisplayName("리스트 반환 테스트")
    void list() {
        //given
        given(menuGroupRepository.findAll()).willReturn(standardMenuGroups);

        //when
        List<MenuGroup> menuGroups = menuGroupService.list();

        //then
        assertThat(menuGroups).hasSize(standardMenuGroups.size()).
            isEqualTo(standardMenuGroups);
    }
}
