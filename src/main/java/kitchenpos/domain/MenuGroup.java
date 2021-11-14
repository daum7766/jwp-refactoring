package kitchenpos.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MenuGroup {

    @Id
    private Long id;

    private String name;

    public MenuGroup() {
    }

    public MenuGroup(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
