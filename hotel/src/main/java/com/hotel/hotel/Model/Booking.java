package com.hotel.hotel.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_number")
    @NotBlank(message = "Booking number is required")
    @Pattern(regexp = "^[A-Z]{3}-\\d{4}$", message = "Booking number must be in format: XXX-XXXX")
    private String bookingNumber;

    @Column(name = "check_in_date")
    @NotNull(message = "Check-in date is required")
    @FutureOrPresent(message = "Check-in date must be in the present or future")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    @NotNull(message = "Check-out date is required")
    @FutureOrPresent(message = "Check-out date must be in the present or future")
    private LocalDate checkOutDate;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @NotNull(message = "Room is required")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "User is required")
    private User user;

}
