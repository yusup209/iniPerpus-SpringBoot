package com.kkp.iniperpus.config;

import com.kkp.iniperpus.model.Book;
import com.kkp.iniperpus.model.Role;
import com.kkp.iniperpus.model.Borrower;
import com.kkp.iniperpus.model.User;
import com.kkp.iniperpus.repository.RoleRepository;
import com.kkp.iniperpus.repository.UserRepository;
import com.kkp.iniperpus.service.BookService;
import com.kkp.iniperpus.service.BorrowerService;
import com.kkp.iniperpus.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserService userService, RoleRepository roleRepository, BookService bookService, BorrowerService borrowerService, UserRepository userRepository) {
        return args -> {
            // Create default role if not exists
            if (roleRepository.findByName("ROLE_USER") == null) {
                Role r = new Role(); r.setName("ROLE_USER"); roleRepository.save(r);
            }

            // Create sample admin user only if no users exist
            if (userRepository.count() == 0) {
                try {
                    User u = new User(); u.setUsername("admin@example.com"); u.setFullName("Admin"); u.setPassword("adminpass"); userService.create(u);
                } catch (Exception ignored) {}
            }

            // Create sample books only if no books exist
            if (bookService.findAll().isEmpty()) {
                try {
                    Book b1 = new Book(); b1.setTitle("Introduction to Java"); b1.setAuthor("Author A"); b1.setIsbn("ISBN-001"); b1.setCopiesTotal(3); b1.setCopiesAvailable(3); bookService.create(b1);
                    Book b2 = new Book(); b2.setTitle("Data Structures"); b2.setAuthor("Author B"); b2.setIsbn("ISBN-002"); b2.setCopiesTotal(2); b2.setCopiesAvailable(2); bookService.create(b2);
                } catch (Exception ignored) {}
            }

            // Create sample borrowers only if no borrowers exist
            if (borrowerService.findAll().isEmpty()) {
                try {
                    Borrower s1 = new Borrower(); s1.setStudentId("S001"); s1.setName("Alice"); s1.setClassName("10A"); borrowerService.save(s1);
                    Borrower s2 = new Borrower(); s2.setStudentId("S002"); s2.setName("Bob"); s2.setClassName("10B"); borrowerService.save(s2);
                } catch (Exception ignored) {}
            }
        };
    }
}
