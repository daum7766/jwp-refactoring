package kitchenpos.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TableGroupDto {
    private Long id;
    private LocalDateTime createdDate;
    private List<OrderTableDto> orderTableDtos;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<OrderTableDto> getOrderTables() {
        return orderTableDtos;
    }

    public void setOrderTables(final List<OrderTableDto> orderTableDtos) {
        this.orderTableDtos = orderTableDtos;
    }
}
