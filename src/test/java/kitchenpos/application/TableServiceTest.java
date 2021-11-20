package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import kitchenpos.domain.OrderTable;
import kitchenpos.dto.OrderTableDto;
import kitchenpos.factory.KitchenPosDtoFactory;
import kitchenpos.factory.KitchenPosFactory;
import kitchenpos.repository.OrderTableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TableServiceTest {

    private final OrderTable standardOrderTable = KitchenPosFactory.getStandardOrderTable();
    private final List<OrderTable> standardOrderTables = KitchenPosFactory.getStandardOrderTables();

    @Mock
    private OrderTableRepository orderTableRepository;

    @InjectMocks
    private TableService tableService;

    @Test
    @DisplayName("테이블을 생성한다.")
    void create() {
        //given
        OrderTableDto request = KitchenPosDtoFactory.getStandardOrderTable();
        given(orderTableRepository.save(any())).willReturn(standardOrderTable);

        //when
        OrderTable orderTable = tableService.create(request);

        //then
        assertThat(orderTable).usingRecursiveComparison()
            .isEqualTo(standardOrderTable);
    }

    @Test
    @DisplayName("모든 테이블을 가져온다.")
    void list() {
        //given
        given(orderTableRepository.findAll()).willReturn(standardOrderTables);

        //when
        List<OrderTable> list = tableService.list();

        //then
        assertThat(list).hasSize(standardOrderTables.size())
            .usingRecursiveComparison()
            .isEqualTo(standardOrderTables);
    }

    @Test
    @DisplayName("테이블의 비어있는 상태를 변경한다")
    void changeEmpty() {
        //given
        OrderTableDto request = KitchenPosDtoFactory.getStandardOrderTable();
        request.setEmpty(true);
        request.setTableGroupId(null);
        given(orderTableDao.findById(1L)).willReturn(Optional.of(request));
        given(orderDao.existsByOrderTableIdAndOrderStatusIn(any(), any())).willReturn(false);
        given(orderTableDao.save(request)).willReturn(request);
        //when
        OrderTableDto orderTableDto = tableService.changeEmpty(1L, standardOrderTableDto);

        //then
        assertThat(orderTableDto.isEmpty()).isFalse();
    }

//    @Test
//    @DisplayName("존재하지 않는 테이블의 비어있는 상태를 변경할때 에러가 발생한다.")
//    void changeEmptyExceptionWithNotExistTableId() {
//        //given
//        OrderTableDto request = KitchenPosDtoFactory.getStandardOrderTable();
//        given(orderTableDao.findById(1L)).willReturn(Optional.empty());
//
//        //when
//        ThrowingCallable callable = () -> tableService.changeEmpty(1L, request);
//
//        //then
//        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
//    }
//
//    @Test
//    @DisplayName("테이블의 비어있는 상태를 변경할때 tableGroupId 가 존재하면 에러가 발생한다.")
//    void changeEmptyExceptionWithExistTableGroupId() {
//        //given
//        OrderTableDto request = KitchenPosDtoFactory.getStandardOrderTable();
//        given(orderTableDao.findById(1L)).willReturn(Optional.of(request));
//
//        //when
//        ThrowingCallable callable = () -> tableService.changeEmpty(1L, request);
//
//        //then
//        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
//    }
//
//    @Test
//    @DisplayName("테이블에서 주문한 상태가 요리중이거나 식사중이라면 에러가 발생한다.")
//    void changeEmptyExceptionWithOrderStatusCookingOrMeal() {
//        //given
//        OrderTableDto request = KitchenPosDtoFactory.getStandardOrderTable();
//        given(orderTableDao.findById(1L)).willReturn(Optional.of(request));
//        given(orderDao.existsByOrderTableIdAndOrderStatusIn(any(), any())).willReturn(true);
//
//        //when
//        ThrowingCallable callable = () -> tableService.changeEmpty(1L, request);
//
//        //then
//        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
//    }

    @Test
    @DisplayName("테이블 인원을 변경한다.")
    void changeNumberOfGuests() {
        //given
        int numberOfGuests = 2;
        OrderTableDto request = KitchenPosDtoFactory.getStandardOrderTable();
        request.setNumberOfGuests(numberOfGuests);
        given(orderTableDao.findById(1L))
            .willReturn(Optional.of(KitchenPosDtoFactory.getStandardOrderTable()));
        given(orderTableDao.save((any()))).willReturn(request);

        //when
        OrderTableDto orderTableDto = tableService.changeNumberOfGuests(1L, request);

        //then
        assertThat(orderTableDto.getNumberOfGuests()).isEqualTo(numberOfGuests);
    }

//    @ParameterizedTest
//    @ValueSource(ints = {-1, -999})
//    @DisplayName("테이블 인원을 0명보다 작게 변경하면 에러가 발생한다.")
//    void changeNumberOfGuestsExceptionWithNumberOfGuestsLessThenZero(int numberOfGuests) {
//        //given
//        OrderTableDto request = KitchenPosDtoFactory.getStandardOrderTable();
//        request.setNumberOfGuests(numberOfGuests);
//
//        //when
//        ThrowingCallable callable = () -> tableService.changeNumberOfGuests(1L, request);
//
//        //then
//        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
//    }
//
//    @Test
//    @DisplayName("존재하지 않는 테이블을 변경하면 에러가 발생한다.")
//    void changeNumberOfGuestsExceptionWithNotExistOrderTable() {
//        //given
//        OrderTableDto request = KitchenPosDtoFactory.getStandardOrderTable();
//        given(orderTableDao.findById(any())).willReturn(Optional.empty());
//
//        //when
//        ThrowingCallable callable = () -> tableService.changeNumberOfGuests(1L, request);
//
//        //then
//        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
//    }
//
//    @Test
//    @DisplayName("비어있는 테이블을 변경하면 에러가 발생한다.")
//    void changeNumberOfGuestsExceptionWithEmpty() {
//        //given
//        OrderTableDto request = KitchenPosDtoFactory.getStandardOrderTable();
//        request.setEmpty(true);
//        given(orderTableDao.findById(any())).willReturn(Optional.of(request));
//
//        //when
//        ThrowingCallable callable = () -> tableService.changeNumberOfGuests(1L, request);
//
//        //then
//        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
//    }
}
