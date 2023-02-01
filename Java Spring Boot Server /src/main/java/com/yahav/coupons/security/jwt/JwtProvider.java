package com.yahav.coupons.security.jwt;

import com.yahav.coupons.models.UserResponseModel;
import com.yahav.coupons.security.UserPrincipal;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface JwtProvider {

    String generateToken(UserPrincipal auth);

    String generateToken(UserResponseModel userResponseModel);

    UserPrincipal getUserPrincipal(HttpServletRequest request);

    Authentication getAuthentication(HttpServletRequest request);

    boolean isTokenValid(HttpServletRequest request);
}
