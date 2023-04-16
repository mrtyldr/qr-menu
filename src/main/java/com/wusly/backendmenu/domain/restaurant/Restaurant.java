package com.wusly.backendmenu.domain.restaurant;

import com.wusly.backendmenu.core.Aggregate;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
@Entity
@Setter
@Getter
public class Restaurant extends Aggregate<UUID> implements UserDetails {

    private String email;

    private String name;

    private String photoUrlLink;

    private String password;

    public Restaurant(UUID id,String email, String name, String photoUrlLink, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.photoUrlLink = photoUrlLink;
        this.password = password;
    }

    public Restaurant() {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("RESTAURANT"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
