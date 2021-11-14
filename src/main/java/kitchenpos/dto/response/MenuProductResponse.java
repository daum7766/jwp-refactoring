package kitchenpos.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.domain.MenuProduct;

public class MenuProductResponse {

    private final Long seq;
    private final Long menuId;
    private final Long productId;
    private final long quantity;

    public MenuProductResponse(Long seq, Long menuId, Long productId, long quantity) {
        this.seq = seq;
        this.menuId = menuId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static MenuProductResponse valueOf(long menuId, MenuProduct menuProduct) {
        return new MenuProductResponse(
            menuProduct.getSeq(),
            menuId,
            menuProduct.getProduct().getId(),
            menuProduct.getQuantity());
    }

    public static List<MenuProductResponse> listOf(long menuId, List<MenuProduct> menuProducts) {
        return menuProducts.stream()
            .map(menuProduct -> valueOf(menuId, menuProduct))
            .collect(Collectors.toList());
    }

    public Long getSeq() {
        return seq;
    }

    public Long getMenuId() {
        return menuId;
    }

    public Long getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }
}
