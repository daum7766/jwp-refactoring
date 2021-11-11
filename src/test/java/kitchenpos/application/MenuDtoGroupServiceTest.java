package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import kitchenpos.dao.MenuGroupDao;
import kitchenpos.dto.MenuGroupDto;
import kitchenpos.factory.KitchenPosFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuDtoGroupServiceTest {

    private final MenuGroupDto standardMenuGroupDto = KitchenPosFactory.getStandardMenuGroup();
    private final List<MenuGroupDto> standardMenuGroupDtos = KitchenPosFactory.getStandardMenuGroups();

    @Mock
    private MenuGroupDao menuGroupDao;

    @InjectMocks
    private MenuGroupService menuGroupService;

    @Test
    @DisplayName("그룹 생성 테스트")
    void create() {
        //given
        MenuGroupDto request = new MenuGroupDto();
        request.setName(standardMenuGroupDto.getName());

        given(menuGroupDao.save(request)).willReturn(standardMenuGroupDto);

        //when
        MenuGroupDto menuGroupDto = menuGroupService.create(request);

        //then
        assertThat(menuGroupDto).isNotNull();
        assertThat(menuGroupDto.getId()).isNotZero();
        assertThat(menuGroupDto).usingRecursiveComparison()
            .isEqualTo(standardMenuGroupDto);
    }

    @Test
    @DisplayName("리스트 반환 테스트")
    void list() {
        //given
        given(menuGroupDao.findAll()).willReturn(standardMenuGroupDtos);

        //when
        List<MenuGroupDto> list = menuGroupService.list();

        //then
        assertThat(list).hasSize(standardMenuGroupDtos.size()).
            containsExactly(standardMenuGroupDto);
    }
}
