package kitchenpos.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import kitchenpos.dto.OrderLineItemDto;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Order {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn
    private OrderTable orderTable;

    private String orderStatus;

    @CreationTimestamp
    private LocalDateTime orderedTime;

    private List<OrderLineItemDto> orderLineItemDtos;

    public boolean isNotCompletion() {
        return !OrderStatus.COMPLETION.equals(orderStatus);
    }

}
