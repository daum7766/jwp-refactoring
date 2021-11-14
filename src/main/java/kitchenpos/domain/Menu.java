package kitchenpos.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Menu {

    @Id
    private Long id;

    private String name;

    private BigDecimal price;

    @ManyToOne
    private MenuGroup menuGroup;

    @OneToMany
    private List<MenuProduct> menuProducts;

    public Menu() {
    }

    private Menu(Builder builder) {
        validate(builder);
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.menuGroup = builder.menuGroup;
        this.menuProducts = new ArrayList<>(builder.menuProducts);
    }

    private void validate(Builder builder) {
        validatePrice(builder.price);
        validatePriceSum(builder.price, builder.menuProducts);
    }

    private void validatePriceSum(BigDecimal price, List<MenuProduct> menuProducts) {
        BigDecimal sum = BigDecimal.ZERO;
        for (MenuProduct menuProduct : menuProducts) {
            sum = sum.add(menuProduct.price());
        }
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
    }

    private static void validatePrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public List<MenuProduct> getMenuProducts() {
        return new ArrayList<>(menuProducts);
    }

    public static class Builder {

        private Long id;
        private String name;
        private BigDecimal price;
        private MenuGroup menuGroup;
        private List<MenuProduct> menuProducts;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder price(double price) {
            this.price = BigDecimal.valueOf(price);
            return this;
        }

        public Builder menuGroup(MenuGroup menuGroup) {
            this.menuGroup = menuGroup;
            return this;
        }

        public Builder menuProducts(List<MenuProduct> menuProducts) {
            this.menuProducts = menuProducts;
            return this;
        }

        public Menu build() {
            return new Menu(this);
        }
    }
}
