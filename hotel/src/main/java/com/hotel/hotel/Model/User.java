package com.hotel.hotel.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]{2,50}$", message = "First name must be 2-50 characters long and contain only letters")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]{2,50}$", message = "Last name must be 2-50 characters long and contain only letters")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Size(max = 100, message = "Email must be less than 100 characters")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
        message = "Password must be at least 8 characters long, contain at least one digit, one uppercase letter, one lowercase letter, one special character, and no whitespace"
    )
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+201[0-2,5]{1}\\d{8}$", message = "Invalid Egyptian phone number format. Must start with +201 followed by valid operator code and 8 digits")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    @Pattern(
        regexp = "^[\\w\\s,.-]{10,255}$",
        message = "Address must be between 10 and 255 characters and can contain letters, numbers, spaces, and basic punctuation"
    )
    @Column(name = "address")
    private String address;

    @NotBlank(message = "User role is required")
    @Pattern(regexp = "^(ADMIN|USER|STAFF)$", message = "User role must be either ADMIN, USER, or STAFF")
    @Column(name = "user_role")
    private String userRole;
}
