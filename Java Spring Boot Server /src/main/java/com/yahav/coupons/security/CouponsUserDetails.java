package com.yahav.coupons.security;

import com.yahav.coupons.entities.UserEntity;
import com.yahav.coupons.services.UserService;
import com.yahav.coupons.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CouponsUserDetails implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CouponsUserDetails(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        try {
            UserEntity userEntity = userService.getUserEntityByUsername(username).get(0);

            Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority(userEntity.getRole().name()));

            return UserPrincipal.builder()
                    .userEntity(userEntity)
                    .id(userEntity.getId())
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .authorities(authorities)
                    .build();

        } catch (Exception e) {
            throw new UsernameNotFoundException(username);
        }
    }

}
