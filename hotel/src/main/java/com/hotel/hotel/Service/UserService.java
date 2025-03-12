package com.hotel.hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import com.hotel.hotel.Model.User;
import com.hotel.hotel.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Map<String, Object> createUser(User user) {
        Map<String, Object> response = new HashMap<>();
        validateUser(user);
        
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        
        response.put("message", "User registered successfully");
        response.put("userId", savedUser.getId());
        response.put("email", savedUser.getEmail());
        return response;
    }

    public Map<String, Object> getUserLogin(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            User user = userOpt.get();
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("email", user.getEmail());
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());
            response.put("role", user.getUserRole());
            return response;
        }
        return null;
    }

    public User updateUser(Long id, User userDetails) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            validateUserUpdate(userDetails);
            User existingUser = userOpt.get();
            
            // Update basic information
            if (userDetails.getFirstName() != null) {
                existingUser.setFirstName(userDetails.getFirstName());
            }
            if (userDetails.getLastName() != null) {
                existingUser.setLastName(userDetails.getLastName());
            }
            if (userDetails.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(userDetails.getPhoneNumber());
            }
            if (userDetails.getAddress() != null) {
                existingUser.setAddress(userDetails.getAddress());
            }
            
            // Update email if changed and not already taken
            if (userDetails.getEmail() != null && !existingUser.getEmail().equals(userDetails.getEmail())) {
                if (userRepository.findByEmail(userDetails.getEmail()).isPresent()) {
                    throw new IllegalArgumentException("Email already exists");
                }
                existingUser.setEmail(userDetails.getEmail());
            }
            
            // Update password if provided
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }
            
            // Update role if provided and user is admin
            if (userDetails.getUserRole() != null) {
                existingUser.setUserRole(userDetails.getUserRole());
            }
            
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByUserRole(role.toUpperCase());
    }

    private void validateUser(User user) {
        if (user.getFirstName() == null || !user.getFirstName().matches("^[a-zA-Z\\s]{2,50}$")) {
            throw new IllegalArgumentException("Invalid first name - must be 2-50 characters and contain only letters");
        }
        if (user.getLastName() == null || !user.getLastName().matches("^[a-zA-Z\\s]{2,50}$")) {
            throw new IllegalArgumentException("Invalid last name - must be 2-50 characters and contain only letters");
        }
        if (user.getEmail() == null || !user.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (user.getPassword() == null || !user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            throw new IllegalArgumentException("Password must be at least 8 characters and contain at least one digit, one uppercase, one lowercase, and one special character");
        }
        if (user.getPhoneNumber() == null || !user.getPhoneNumber().matches("^\\+201[0-2,5]{1}\\d{8}$")) {
            throw new IllegalArgumentException("Invalid Egyptian phone number format - must start with +201");
        }
        if (user.getAddress() == null || !user.getAddress().matches("^[\\w\\s,.-]{10,255}$")) {
            throw new IllegalArgumentException("Address must be between 10 and 255 characters");
        }
        if (user.getUserRole() == null || !user.getUserRole().matches("^(ADMIN|USER|STAFF)$")) {
            throw new IllegalArgumentException("User role must be either ADMIN, USER, or STAFF");
        }
    }

    private void validateUserUpdate(User user) {
        if (user.getFirstName() != null && !user.getFirstName().matches("^[a-zA-Z\\s]{2,50}$")) {
            throw new IllegalArgumentException("Invalid first name - must be 2-50 characters and contain only letters");
        }
        if (user.getLastName() != null && !user.getLastName().matches("^[a-zA-Z\\s]{2,50}$")) {
            throw new IllegalArgumentException("Invalid last name - must be 2-50 characters and contain only letters");
        }
        if (user.getEmail() != null && !user.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty() && 
            !user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            throw new IllegalArgumentException("Password must be at least 8 characters and contain at least one digit, one uppercase, one lowercase, and one special character");
        }
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().matches("^\\+201[0-2,5]{1}\\d{8}$")) {
            throw new IllegalArgumentException("Invalid Egyptian phone number format - must start with +201");
        }
        if (user.getAddress() != null && !user.getAddress().matches("^[\\w\\s,.-]{10,255}$")) {
            throw new IllegalArgumentException("Address must be between 10 and 255 characters");
        }
        if (user.getUserRole() != null && !user.getUserRole().matches("^(ADMIN|USER|STAFF)$")) {
            throw new IllegalArgumentException("User role must be either ADMIN, USER, or STAFF");
        }
    }
}
