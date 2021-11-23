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

    private OrderStatus orderStatus;

    @CreationTimestamp
    private LocalDateTime orderedTime;

//    private List<OrderLineItemDto> orderLineItemDtos;


    public Order() {
    }

    public Order(Long id, OrderStatus orderStatus) {
        this.id = id;
        this.orderStatus = orderStatus;
    }

    public boolean isNotCompletion() {
        return !OrderStatus.COMPLETION.equals(orderStatus);
    }
}
