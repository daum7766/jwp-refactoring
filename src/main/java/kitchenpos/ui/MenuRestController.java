package kitchenpos.ui;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.application.MenuService;
import kitchenpos.domain.Menu;
import kitchenpos.dto.MenuDto;
import kitchenpos.dto.response.MenuResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuRestController {

    private final MenuService menuService;

    public MenuRestController(final MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/api/menus")
    public ResponseEntity<MenuResponse> create(@RequestBody final MenuDto menuDto) {
        final Menu created = menuService.create(menuDto);
        final URI uri = URI.create("/api/menus/" + created.getId());
        return ResponseEntity.created(uri)
            .body(MenuResponse.valueOf(created));
    }

    @GetMapping("/api/menus")
    public ResponseEntity<List<MenuResponse>> list() {
        List<MenuResponse> menuResponses = menuService.list()
            .stream()
            .map(MenuResponse::valueOf)
            .collect(Collectors.toList());

        return ResponseEntity.ok()
            .body(menuResponses);
    }
}
