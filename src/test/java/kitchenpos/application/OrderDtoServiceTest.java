package kitchenpos.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kitchenpos.dao.MenuDao;
import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderLineItemDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.dto.OrderDto;
import kitchenpos.domain.OrderStatus;
import kitchenpos.dto.OrderTableDto;
import kitchenpos.factory.KitchenPosDtoFactory;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderDtoServiceTest {

    private final OrderDto standardOrderDto = KitchenPosDtoFactory.getStandardOrder();
    private final List<OrderDto> standardOrderDtos = KitchenPosDtoFactory.getStandardOrders();

    @Mock
    private MenuDao menuDao;

    @Mock
    private OrderDao orderDao;

    @Mock
    private OrderLineItemDao orderLineItemDao;

    @Mock
    private OrderTableDao orderTableDao;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("주문을 생성한다.")
    void create() {
        //given
        given(menuDao.countByIdIn(any())).willReturn(1L);
        given(orderTableDao.findById(1L)).willReturn(Optional.of(KitchenPosDtoFactory.getStandardOrderTable()));
        given(orderDao.save(standardOrderDto)).willReturn(standardOrderDto);
        given(orderLineItemDao.save(any())).willReturn(KitchenPosDtoFactory.getStandardOrderLineItem());

        //when
        OrderDto orderDto = orderService.create(standardOrderDto);

        //then
        assertThat(orderDto).usingRecursiveComparison()
            .isEqualTo(standardOrderDto);
    }

    @Test
    @DisplayName("OrderLineItems 가 비어있다면 주문 생성시 에러가 발생한다.")
    void createExceptionWithEmptyOrderLineItems() {
        //given
        OrderDto request = KitchenPosDtoFactory.getStandardOrder();
        request.setOrderLineItems(new ArrayList<>());

        //when
        ThrowingCallable callable = () -> orderService.create(request);

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주문한 메뉴 id size 와 db에 저장된 size 가 다르다면 주문 생성시 에러가 발생한다.")
    void createExceptionWithNotEqualsItemSize() {
        //given
        OrderDto request = KitchenPosDtoFactory.getStandardOrder();
        given(menuDao.countByIdIn(any())).willReturn(1L);
        given(orderTableDao.findById(1L)).willReturn(Optional.empty());

        //when
        ThrowingCallable callable = () -> orderService.create(request);

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주문한 테이블이 없다면 주문 생성시 에러가 발생한다.")
    void createExceptionWithNotExistOrderTable() {
        //given
        OrderDto request = KitchenPosDtoFactory.getStandardOrder();
        request.setOrderTableId(2L);
        given(menuDao.countByIdIn(any())).willReturn(1L);
        given(orderTableDao.findById(2L)).willReturn(Optional.empty());

        //when
        ThrowingCallable callable = () -> orderService.create(request);

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주문한 테이블이 비어있다면 주문 생성시 에러가 발생한다.")
    void createExceptionWithOrderTableEmpty() {
        //given
        OrderTableDto requestOrderTableDto = KitchenPosDtoFactory.getStandardOrderTable();
        requestOrderTableDto.setEmpty(true);
        given(menuDao.countByIdIn(any())).willReturn(1L);
        given(orderTableDao.findById(1L)).willReturn(Optional.of(requestOrderTableDto));

        //when
        ThrowingCallable callable = () -> orderService.create(standardOrderDto);

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("모든 주문을 가져온다.")
    void list() {
        //given
        given(orderDao.findAll()).willReturn(standardOrderDtos);
        given(orderLineItemDao.findAllByOrderId(1L)).willReturn(KitchenPosDtoFactory.getStandardOrderLineItems());

        //when
        List<OrderDto> orderDtos = orderService.list();

        //then
        assertThat(orderDtos).usingRecursiveComparison()
            .isEqualTo(standardOrderDtos);
    }

    @Test
    @DisplayName("주문 상태를 변경한다.")
    void changeOrderStatus() {
        //given
        OrderDto request = KitchenPosDtoFactory.getStandardOrder();
        request.setOrderStatus(OrderStatus.MEAL.name());
        given(orderDao.findById(1L)).willReturn(Optional.of(standardOrderDto));
        given(orderDao.save(standardOrderDto)).willReturn(request);
        given(orderLineItemDao.findAllByOrderId(1L)).willReturn(KitchenPosDtoFactory.getStandardOrderLineItems());

        //when
        OrderDto orderDto = orderService.changeOrderStatus(1L, request);

        //then
        assertThat(orderDto).usingRecursiveComparison()
            .isEqualTo(standardOrderDto);
    }

    @Test
    @DisplayName("존재하지 않는 주문을 수정하면 에러가 발생한다.")
    void changeOrderStatusExceptionWithNotExistOrder() {
        //given
        OrderDto request = KitchenPosDtoFactory.getStandardOrder();
        given(orderDao.findById(2L)).willReturn(Optional.empty());

        //when
        ThrowingCallable callable = () -> orderService.changeOrderStatus(2L, request);

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("완료 상태의 주문을 수정하면 에러가 발생한다.")
    void changeOrderStatusExceptionWithOrderStatusCompletion() {
        //given
        OrderDto request = KitchenPosDtoFactory.getStandardOrder();
        request.setOrderStatus(OrderStatus.COMPLETION.name());
        given(orderDao.findById(1L)).willReturn(Optional.of(request));

        //when
        ThrowingCallable callable = () -> orderService.changeOrderStatus(1L, request);

        //then
        assertThatThrownBy(callable).isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
