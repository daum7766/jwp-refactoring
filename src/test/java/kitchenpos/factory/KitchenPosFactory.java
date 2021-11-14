package kitchenpos.factory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.Product;
import kitchenpos.dto.MenuDto;
import kitchenpos.dto.MenuProductDto;
import kitchenpos.dto.OrderDto;
import kitchenpos.dto.OrderLineItemDto;
import kitchenpos.dto.OrderTableDto;
import kitchenpos.dto.TableGroupDto;

public class KitchenPosFactory {

    private KitchenPosFactory() {
    }

    public static Product getStandardProduct() {
        return new Product(1L, "상품이름", new BigDecimal(1000L));
    }

    public static List<Product> getStandardProducts() {
        List<Product> standardProducts = new ArrayList<>();
        standardProducts.add(getStandardProduct());
        return standardProducts;
    }

    public static MenuGroup getStandardMenuGroup() {
        return new MenuGroup(1L, "메뉴그룹이름");
    }

    public static List<MenuGroup> getStandardMenuGroups() {
        List<MenuGroup> standardMenuGroups = new ArrayList<>();
        standardMenuGroups.add(getStandardMenuGroup());
        return standardMenuGroups;
    }

    public static MenuProduct getStandardMenuProduct() {
        return new MenuProduct(1L, getStandardProduct(), 1);
    }

    public static List<MenuProduct> getStandardMenuProducts() {
        List<MenuProduct> standardMenuProducts = new ArrayList<>();
        standardMenuProducts.add(getStandardMenuProduct());
        return standardMenuProducts;
    }

    public static Menu getStandardMenu() {
        return new Menu.Builder()
            .menuGroup(getStandardMenuGroup())
            .id(1L)
            .price(1000L)
            .name("메뉴이름")
            .menuProducts(getStandardMenuProducts())
            .build();
    }

    public static List<Menu> getStandardMenus() {
        List<Menu> standardMenus = new ArrayList<>();
        standardMenus.add(getStandardMenu());
        return standardMenus;
    }

    public static OrderDto getStandardOrder() {
        OrderDto standardOrderDto = new OrderDto();
        standardOrderDto.setId(1L);
        standardOrderDto.setOrderTableId(1L);
        standardOrderDto.setOrderedTime(LocalDateTime.now());
        standardOrderDto.setOrderLineItems(getStandardOrderLineItems());
        standardOrderDto.setOrderStatus(OrderStatus.COOKING.name());
        return standardOrderDto;
    }

    public static List<OrderDto> getStandardOrders() {
        List<OrderDto> standardOrderDtos = new ArrayList<>();
        standardOrderDtos.add(getStandardOrder());
        return standardOrderDtos;
    }

    public static OrderLineItemDto getStandardOrderLineItem() {
        OrderLineItemDto orderLineItemDto = new OrderLineItemDto();
        orderLineItemDto.setOrderId(1L);
        orderLineItemDto.setMenuId(1L);
        orderLineItemDto.setQuantity(1L);
        orderLineItemDto.setSeq(1L);
        return orderLineItemDto;
    }

    public static List<OrderLineItemDto> getStandardOrderLineItems() {
        List<OrderLineItemDto> standardOrderLineItemDtos = new ArrayList<>();
        standardOrderLineItemDtos.add(getStandardOrderLineItem());
        return standardOrderLineItemDtos;
    }

    public static OrderTableDto getStandardOrderTable() {
        OrderTableDto standardOrderTableDto = new OrderTableDto();
        standardOrderTableDto.setId(1L);
        standardOrderTableDto.setTableGroupId(1L);
        standardOrderTableDto.setEmpty(false);
        standardOrderTableDto.setNumberOfGuests(1);
        return standardOrderTableDto;
    }

    public static List<OrderTableDto> getStandardOrderTables() {
        List<OrderTableDto> standardOrderTableDtos = new ArrayList<>();
        standardOrderTableDtos.add(getStandardOrderTable());
        return standardOrderTableDtos;
    }

    public static TableGroupDto getStandardTableGroup() {
        List<OrderTableDto> standardOrderTableDtos = getStandardOrderTables();
        OrderTableDto standardOrderTableDto = getStandardOrderTable();
        standardOrderTableDto.setId(2L);
        standardOrderTableDto.setTableGroupId(null);
        standardOrderTableDto.setEmpty(true);
        standardOrderTableDtos.get(0).setEmpty(true);
        standardOrderTableDtos.get(0).setTableGroupId(null);
        standardOrderTableDtos.add(standardOrderTableDto);

        TableGroupDto standardTableGroupDto = new TableGroupDto();
        standardTableGroupDto.setId(1L);

        standardTableGroupDto.setOrderTables(standardOrderTableDtos);
        standardTableGroupDto.setCreatedDate(LocalDateTime.now());
        return standardTableGroupDto;
    }
}
