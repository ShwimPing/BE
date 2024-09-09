package com.shwimping.be.auth.presentation.filter;

import com.shwimping.be.auth.application.jwt.JwtUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    public UserAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public static UserAuthentication from(JwtUserDetails jwtUserDetails) {
        return new UserAuthentication(jwtUserDetails.userId(), "", jwtUserDetails.role().getAuthority());
    }
}