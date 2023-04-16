package com.wusly.backendmenu.domain.category;

import com.wusly.backendmenu.core.Aggregate;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Entity
@Getter
@Setter
public class Category extends Aggregate<UUID> {
    private String name;

    public Category(UUID id,String name) {
        this.id = id;
        this.name = name;
    }

    public Category() {
    }

}
