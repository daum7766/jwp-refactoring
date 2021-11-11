package kitchenpos.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MenuProduct {

    @Id
    private Long seq;

    @ManyToOne
    private Menu menu;

    @ManyToOne
    private Product product;

    private long quantity;

    public BigDecimal price() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
