package kitchenpos.ui;

import kitchenpos.application.OrderService;
import kitchenpos.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/orderDtos")
    public ResponseEntity<OrderDto> create(@RequestBody final OrderDto orderDto) {
        final OrderDto created = orderService.create(orderDto);
        final URI uri = URI.create("/api/orderDtos/" + created.getId());
        return ResponseEntity.created(uri)
                .body(created)
                ;
    }

    @GetMapping("/api/orderDtos")
    public ResponseEntity<List<OrderDto>> list() {
        return ResponseEntity.ok()
                .body(orderService.list())
                ;
    }

    @PutMapping("/api/orderDtos/{orderId}/order-status")
    public ResponseEntity<OrderDto> changeOrderStatus(
            @PathVariable final Long orderId,
            @RequestBody final OrderDto orderDto
    ) {
        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, orderDto));
    }
}
