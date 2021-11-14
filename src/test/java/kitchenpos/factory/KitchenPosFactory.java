package kitchenpos.factory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.Product;
import kitchenpos.dto.MenuDto;
import kitchenpos.dto.MenuGroupDto;
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

    public static MenuProductDto getStandardMenuProduct() {
        MenuProductDto standardMenuProductDto = new MenuProductDto();
        standardMenuProductDto.setProductId(1L);
        standardMenuProductDto.setMenuId(1L);
        standardMenuProductDto.setSeq(1L);
        standardMenuProductDto.setQuantity(1L);
        return standardMenuProductDto;
    }

    public static List<MenuProductDto> getStandardMenuProducts() {
        List<MenuProductDto> standardMenuProductDtos = new ArrayList<>();
        standardMenuProductDtos.add(getStandardMenuProduct());
        return standardMenuProductDtos;
    }

    public static MenuDto getStandardMenu() {
        MenuDto standardMenuDto = new MenuDto();
        standardMenuDto.setName("메뉴이름");
        standardMenuDto.setId(1L);
        standardMenuDto.setPrice(new BigDecimal(1000));
        standardMenuDto.setMenuGroupId(1L);
        standardMenuDto.setMenuProducts(getStandardMenuProducts());
        return standardMenuDto;
    }

    public static List<MenuDto> getStandardMenus() {
        List<MenuDto> standardMenuDtos = new ArrayList<>();
        standardMenuDtos.add(getStandardMenu());
        return standardMenuDtos;
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
