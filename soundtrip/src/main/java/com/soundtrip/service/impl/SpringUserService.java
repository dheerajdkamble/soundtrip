package com.soundtrip.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.soundtrip.dto.User;
import com.soundtrip.service.UserService;

/**
 * UserService that accesses the spring credentials.
 */
@Service
public class SpringUserService implements UserService {

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return new User("");
        }

        return new User(((UserDetails) authentication.getPrincipal()).getUsername());
    }
}
