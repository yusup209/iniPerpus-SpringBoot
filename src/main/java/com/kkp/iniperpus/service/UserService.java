package com.kkp.iniperpus.service;

import com.kkp.iniperpus.model.Role;
import com.kkp.iniperpus.model.User;
import com.kkp.iniperpus.repository.RoleRepository;
import com.kkp.iniperpus.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        // Do not mutate entity passwords; controllers should sanitize responses
        return userRepository.findAll();
    }

    public User findById(Long id) { return userRepository.findById(id).orElse(null); }

    public User create(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) throw new IllegalArgumentException("username exists");
        
        String rawPassword = user.getPassword() == null ? "password" : user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        System.out.println("DEBUG UserService.create:");
        System.out.println("  Username: " + user.getUsername());
        System.out.println("  Raw password: " + rawPassword);
        System.out.println("  Encoded password: " + encodedPassword);
        
        user.setPassword(encodedPassword);
        
        Role r = roleRepository.findByName("ROLE_USER");
        if (r == null) { 
            r = new Role(); 
            r.setName("ROLE_USER"); 
            roleRepository.save(r); 
            System.out.println("  Created new ROLE_USER");
        }
        user.getRoles().add(r);
        System.out.println("  Roles count: " + user.getRoles().size());
        
        User saved = userRepository.save(user);
        System.out.println("  User saved with ID: " + saved.getId());
        
        saved.setPassword(null);
        return saved;
    }

    public void delete(Long id) { userRepository.deleteById(id); }
}
