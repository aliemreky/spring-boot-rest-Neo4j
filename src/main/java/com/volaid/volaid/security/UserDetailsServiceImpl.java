package com.volaid.volaid.security;

import com.volaid.volaid.entity.User;
import com.volaid.volaid.repository.UserRepository;
import org.apache.log4j.Logger;
import org.neo4j.graphdb.NotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static Logger LOGGER = Logger.getLogger(UserDetailsServiceImpl.class);

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LOGGER.info("Looking for the User: " + username + " from DB...");

        Optional<User> normalUser = userRepository.findByUsername(username);

        if(!normalUser.isPresent()){
            throw new NotFoundException("The user has not found");
        }

        LOGGER.info("User: " + username + " has found");

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Roles.ROLE_USER.getAuthority()));

        return new org.springframework.security.core.userdetails.User(normalUser.get().getUsername(), normalUser.get().getPassword(), authorities);

    }

}