package com.wusly.backendmenu.domain.restaurant;


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

    public void update(String firstUrl, String secondUrl, String photoLink) {
        this.firstUrl = firstUrl;
        this.secondUrl = secondUrl;
        this.photoUrl = photoLink;
    }
}
