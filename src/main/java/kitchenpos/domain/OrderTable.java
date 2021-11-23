package kitchenpos.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class OrderTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private TableGroup tableGroup;

    @OneToOne
    private Order order;

    private int numberOfGuests;

    private boolean empty;

    public OrderTable() {
    }

    public OrderTable(int numberOfGuests, boolean empty) {
        this(null, null, null, numberOfGuests, empty);
    }

    private OrderTable(Builder builder) {
        this(builder.id,
            builder.tableGroup,
            builder.order,
            builder.numberOfGuests,
            builder.empty);
    }

    public OrderTable(Long id, TableGroup tableGroup, Order order, int numberOfGuests, boolean empty) {
        this.id = id;
        this.tableGroup = tableGroup;
        this.order = order;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public void changeEmpty(boolean empty) {
        validateChangeEmpty();
        this.empty = empty;
    }

    private void validateChangeEmpty() {
        validateTableGroup();
        validateState();
    }

    private void validateTableGroup() {
        if (Objects.nonNull(tableGroup)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateState() {
        if (order.isNotCompletion()) {
            throw new IllegalArgumentException();
        }
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        validateChangeNumberOfGuests(numberOfGuests);
        this.numberOfGuests = numberOfGuests;
    }

    private void validateChangeNumberOfGuests(int numberOfGuests) {
        validateNumberOfGuests(numberOfGuests);
        validateOrderTable();
    }

    private void validateNumberOfGuests(int numberOfGuests) {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
    }

    private void validateOrderTable() {
        if (isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public Long getId() {
        return id;
    }

    public TableGroup getTableGroup() {
        return tableGroup;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }

    public Order getOrder() {
        return order;
    }

    public static class Builder {

        private Long id;
        private TableGroup tableGroup;
        private Order order;
        private int numberOfGuests;
        private boolean empty;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder tableGroup(TableGroup tableGroup) {
            this.tableGroup = tableGroup;
            return this;
        }

        public Builder order(Order order) {
            this.order = order;
            return this;
        }

        public Builder numberOfGuests(int numberOfGuests) {
            this.numberOfGuests = numberOfGuests;
            return this;
        }

        public Builder empty(boolean empty) {
            this.empty = empty;
            return this;
        }

        public OrderTable build() {
            return new OrderTable(this);
        }
    }
}
