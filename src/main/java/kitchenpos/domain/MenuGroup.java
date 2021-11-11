package kitchenpos.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MenuGroup {
    @Id
    private Long id;

    private String name;


}
