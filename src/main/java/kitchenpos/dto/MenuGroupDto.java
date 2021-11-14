package kitchenpos.dto;

import kitchenpos.domain.MenuGroup;

public class MenuGroupDto {

    private Long id;
    private String name;

    public MenuGroup toMenuGroup() {
        return new MenuGroup(id, name);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
