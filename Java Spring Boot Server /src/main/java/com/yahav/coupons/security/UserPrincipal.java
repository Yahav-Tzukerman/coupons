package com.yahav.coupons.security;

import com.yahav.coupons.entities.UserEntity;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.enums.Role;
import com.yahav.coupons.exceptions.ServerException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {

    private long id;
    private String username;
    transient private String password;
    transient private UserEntity userEntity;
    private Set<GrantedAuthority> authorities;


    public Role getUserRole() throws ServerException {
        return getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> Arrays.asList(authority.split("_")))
                .map(Collection::stream)
                .map(stream -> stream.skip(1))
                .flatMap(Stream::findFirst)
                .map(Role::valueOf)
                .orElseThrow(() -> new ServerException(ErrorType.GENERAL_ERROR));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
