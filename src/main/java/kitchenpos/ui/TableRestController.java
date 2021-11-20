package kitchenpos.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.application.TableService;
import kitchenpos.domain.OrderTable;
import kitchenpos.dto.OrderTableDto;
import kitchenpos.dto.response.OrderTableResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableRestController {

    private final TableService tableService;

    public TableRestController(final TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping("/api/tables")
    public ResponseEntity<OrderTableResponse> create(
        @RequestBody final OrderTableDto orderTableDto) {
        final OrderTable created = tableService.create(orderTableDto);
        final URI uri = URI.create("/api/tables/" + created.getId());
        return ResponseEntity.created(uri)
            .body(OrderTableResponse.valueOf(created));
    }

    @GetMapping("/api/tables")
    public ResponseEntity<List<OrderTableResponse>> list() {
        List<OrderTableResponse> orderTableResponses = tableService.list()
            .stream()
            .map(OrderTableResponse::valueOf)
            .collect(Collectors.toList());

        return ResponseEntity.ok()
            .body(orderTableResponses);
    }

    @PutMapping("/api/tables/{orderTableId}/empty")
    public ResponseEntity<OrderTableResponse> changeEmpty(
        @PathVariable final Long orderTableId, @RequestBody final OrderTableDto orderTableDto) {
        OrderTable orderTable = tableService.changeEmpty(orderTableId, orderTableDto);
        return ResponseEntity.ok()
            .body(OrderTableResponse.valueOf(orderTable));
    }

    @PutMapping("/api/tables/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTableResponse> changeNumberOfGuests(
        @PathVariable final Long orderTableId, @RequestBody final OrderTableDto orderTableDto) {
        OrderTable orderTable = tableService.changeNumberOfGuests(orderTableId, orderTableDto);
        return ResponseEntity.ok()
            .body(OrderTableResponse.valueOf(orderTable));
    }
}
