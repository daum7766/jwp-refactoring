package kitchenpos.application;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.Product;
import kitchenpos.dto.MenuDto;
import kitchenpos.repository.MenuGroupRepository;
import kitchenpos.repository.MenuProductRepository;
import kitchenpos.repository.MenuRepository;
import kitchenpos.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    private final MenuGroupRepository menuGroupRepository;
    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;
    private final MenuProductRepository menuProductRepository;

    public MenuService(
        final MenuGroupRepository menuGroupRepository,
        final MenuRepository menuRepository,
        final ProductRepository productRepository,
        final MenuProductRepository menuProductRepository
    ) {
        this.menuGroupRepository = menuGroupRepository;
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
        this.menuProductRepository = menuProductRepository;
    }

    @Transactional
    public Menu create(final MenuDto menuDto) {

        final MenuGroup menuGroup = menuGroupRepository.findById(menuDto.getMenuGroupId())
            .orElseThrow(IllegalArgumentException::new);

        List<MenuProduct> menuProducts = menuDto.getMenuProducts()
            .stream()
            .map(menuProductDto -> {
                Product product = productRepository.findById(menuProductDto.getProductId())
                    .orElseThrow(IllegalArgumentException::new);
                MenuProduct menuProduct = new MenuProduct(product, menuProductDto.getQuantity());
                return menuProductRepository.save(menuProduct);
            })
            .collect(Collectors.toList());

        Menu menu = new Menu.Builder()
            .menuGroup(menuGroup)
            .menuProducts(menuProducts)
            .name(menuDto.getName())
            .price(menuDto.getPrice())
            .build();

        return menuRepository.save(menu);
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }
}
