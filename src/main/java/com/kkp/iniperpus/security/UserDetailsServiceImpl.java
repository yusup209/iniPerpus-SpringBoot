package com.kkp.iniperpus.security;

import com.kkp.iniperpus.model.User;
import com.kkp.iniperpus.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("DEBUG: User not found for username: " + username);
            throw new UsernameNotFoundException("User not found");
        }

        System.out.println("DEBUG: User found: " + user.getUsername() + ", password hash: " + user.getPassword());
        System.out.println("DEBUG: User roles: " + (user.getRoles() != null ? user.getRoles().size() : "null"));

        Collection<GrantedAuthority> authorities = user.getRoles() != null 
                ? user.getRoles().stream()
                    .map(r -> new SimpleGrantedAuthority(r.getName()))
                    .collect(Collectors.toSet())
                : Collections.emptySet();

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), 
            user.getPassword(), 
            true, true, true, true,
            authorities
        );
    }
}
