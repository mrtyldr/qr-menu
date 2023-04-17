package com.wusly.backendmenu.domain.restaurant;


import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class RestaurantSettings {
    @Id
    UUID restaurantId;
    String firstUrl;
    String secondUrl;
    String photoUrl;
}
