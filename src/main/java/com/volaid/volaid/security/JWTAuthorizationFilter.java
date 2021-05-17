package com.volaid.volaid.security;

import com.volaid.volaid.util.ServiceConstants;
import com.volaid.volaid.util.ServiceUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static Logger LOGGER = Logger.getLogger(JWTAuthorizationFilter.class);

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(ServiceConstants.HEADER_STRING);
        if (header == null || !header.startsWith(ServiceConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        LOGGER.info("Getting token...");

        String token = request.getHeader(ServiceConstants.HEADER_STRING);

        if (token != null) {
            LOGGER.info("Token is gathered");

            Claims claims = Jwts.parser()
                    .setSigningKey(ServiceUtils.stringTransformForEncryptionOfSecretKey(ServiceConstants.SECRET_KEY, JWTAuthenticationFilter.randomNumber))
                    .parseClaimsJws(token.replace(ServiceConstants.TOKEN_PREFIX, ""))
                    .getBody();

            LOGGER.info("Got payload: " + claims.toString());

            Collection<? extends GrantedAuthority> authorities
                    = Arrays.asList(claims.get("role").toString().split(",")).stream()
                    .map(authority -> new SimpleGrantedAuthority(authority))
                    .collect(Collectors.toList());

            LOGGER.info("Extracted authorities: " + authorities.toString());

            User principal = new User(claims.getSubject(), "", authorities);

            if (principal != null) {
                return new UsernamePasswordAuthenticationToken(principal, null, authorities);
            }
            return null;
        }
        return null;
    }
}