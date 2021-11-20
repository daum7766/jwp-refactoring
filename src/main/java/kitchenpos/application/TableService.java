package kitchenpos.application;

import java.util.List;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderTable;
import kitchenpos.dto.OrderTableDto;
import kitchenpos.repository.OrderRepository;
import kitchenpos.repository.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableService {

    private final OrderTableRepository orderTableRepository;


    public TableService(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public OrderTable create(final OrderTableDto orderTableDto) {
        OrderTable orderTable = new OrderTable(orderTableDto.getNumberOfGuests(),
            orderTableDto.isEmpty());
        return orderTableRepository.save(orderTable);
    }

    public List<OrderTable> list() {
        return orderTableRepository.findAll();
    }

    @Transactional
    public OrderTable changeEmpty(final Long orderTableId, final OrderTableDto orderTableDto) {
        OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(IllegalAccessError::new);

        orderTable.changeEmpty(orderTableDto.isEmpty());

        return orderTable;
    }

    @Transactional
    public OrderTable changeNumberOfGuests(final Long orderTableId,
        final OrderTableDto orderTableDto) {
        OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(IllegalArgumentException::new);

        orderTable.changeNumberOfGuests(orderTableDto.getNumberOfGuests());

        return orderTable;
    }
}
