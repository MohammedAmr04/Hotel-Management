package com.hotel.hotel.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Room number is required")
    @Pattern(regexp = "^[A-Z]\\d{3}$", message = "Room number must be in format: 1 uppercase letter followed by 3 digits (e.g., A101)")
    @Column(name = "room_number", unique = true)
    private String roomNumber;

    @NotBlank(message = "Room type is required")
    @Pattern(regexp = "^(SINGLE|DOUBLE|SUITE|DELUXE)$", message = "Room type must be either SINGLE, DOUBLE, SUITE, or DELUXE")
    @Column(name = "room_type")
    private String roomType;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 6, message = "Capacity cannot exceed 6")
    @Column(name = "capacity")
    private Integer capacity;

    @NotNull(message = "Price per night is required")
    @DecimalMin(value = "100.0", message = "Price per night must be at least 100")
    @DecimalMax(value = "10000.0", message = "Price per night cannot exceed 10000")
    @Column(name = "price_per_night")
    private Double pricePerNight;

    @NotBlank(message = "Room status is required")
    @Pattern(regexp = "^(AVAILABLE|OCCUPIED|MAINTENANCE|RESERVED)$", 
            message = "Room status must be either AVAILABLE, OCCUPIED, MAINTENANCE, or RESERVED")
    @Column(name = "room_status")
    private String roomStatus;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Column(name = "description")
    private String description;

    @Pattern(regexp = "^(YES|NO)$", message = "Smoking allowed must be either YES or NO")
    @Column(name = "smoking_allowed")
    private String smokingAllowed;

    @NotNull(message = "Floor number is required")
    @Min(value = 1, message = "Floor number must be at least 1")
    @Max(value = 20, message = "Floor number cannot exceed 20")
    @Column(name = "floor_number")
    private Integer floorNumber;
}
