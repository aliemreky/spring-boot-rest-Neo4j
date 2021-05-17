package com.volaid.volaid.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volaid.volaid.entity.User;
import com.volaid.volaid.util.ServiceConstants;
import com.volaid.volaid.util.ServiceUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static int randomNumber = getRandomNumber(5);

    private AuthenticationManager authenticationManager;

    private static Logger LOGGER = Logger.getLogger(JWTAuthenticationFilter.class);

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            LOGGER.info("Attempting to authenticate...");

            User credentials = new ObjectMapper().readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            new ArrayList<>())
            );
        } catch (Exception e) {
            LOGGER.error("Authentication has failed: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {

        LOGGER.info("Building JWT...");

        Map<String, Object> authorities = authorizationGetter(auth);

        if (authorities != null) {

            String token = Jwts.builder()
                    .setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                    .addClaims(authorities)
                    .setExpiration(new Date(System.currentTimeMillis() + ServiceConstants.EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, ServiceUtils.stringTransformForEncryptionOfSecretKey(ServiceConstants.SECRET_KEY, randomNumber))
                    .compact();
            res.addHeader(ServiceConstants.HEADER_STRING, ServiceConstants.TOKEN_PREFIX + token);

            LOGGER.info("JWT is: " + token);
        } else {
            LOGGER.info("There are no authorities (authorities = null)");
        }

        LOGGER.info("Authentication is successful");
    }

    private Map<String, Object> authorizationGetter(Authentication auth) {
        String modOrUser = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority(Roles.ROLE_USER.getAuthority())) ? "ROLE_USER" : "ROLE_MOD";

        Map<String, Object> authorities = new HashMap<>();
        authorities.put("role", modOrUser);

        LOGGER.info("Authorities are: " + authorities.toString());

        return authorities;
    }

    private static int getRandomNumber(int maximumValue) {
        Random rand = new Random();

        int n = rand.nextInt(maximumValue) + 1;

        return n;
    }

}