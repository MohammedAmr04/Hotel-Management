package com.hotel.hotel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hotel.hotel.Model.Booking;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;
import com.hotel.hotel.Model.Room;
import com.hotel.hotel.Model.User;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingNumber(String bookingNumber);
    List<Booking> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate);
    List<Booking> findByCheckOutDateBetween(LocalDate startDate, LocalDate endDate);
    List<Booking> findByRoom(Room room);
    List<Booking> findByUser(User user);
}
