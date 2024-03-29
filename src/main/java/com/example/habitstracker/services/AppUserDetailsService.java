package com.example.habitstracker.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.habitstracker.repository.UserRepository;

@Component
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public AppUserDetailsService(UserRepository userRepository) {
        // todo service
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with " + username + " doesn't exists.");
        }
        com.example.habitstracker.models.User user1 = user.get();
        return new User(user1.getUsername(), user1.getPassword(), new ArrayList<>());
    }
}
