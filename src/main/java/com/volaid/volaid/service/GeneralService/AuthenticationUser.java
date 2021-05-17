package com.volaid.volaid.service.GeneralService;

import com.volaid.volaid.entity.User;
import com.volaid.volaid.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationUser {

    @Autowired
    private UserRepository userRepository;

    public User getAuthUser() {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();

        Optional<User> authUser = userRepository.findByUsername(username);

        return authUser.get();

    }

}
