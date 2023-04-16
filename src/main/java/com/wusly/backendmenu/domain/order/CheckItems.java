package com.wusly.backendmenu.domain.order;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckItems {
        @Id
        UUID id;
        UUID itemId;
        Integer quantity;

        public static CheckItems of(UUID itemId, Integer quantity){
            return new CheckItems(UUID.randomUUID(),itemId,quantity);
        }

}
