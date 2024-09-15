package com.management.product.datasources.domain.schema;

import com.management.product.datasources.domain.entity.AdminEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
public class AdminDetailDTO implements UserDetails {

    private final AdminEntity adminEntity;

    public AdminDetailDTO(AdminEntity adminEntity){
        this.adminEntity = adminEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + adminEntity.getRole().toString()));
    }

    @Override
    public String getPassword() {
        return adminEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return adminEntity.getUsername();
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

    public UUID getId() {
        return adminEntity.getId();
    }
}
