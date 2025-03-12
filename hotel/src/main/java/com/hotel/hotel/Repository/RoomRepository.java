package com.hotel.hotel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hotel.hotel.Model.Room;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByRoomType(String roomType);
    List<Room> findByRoomStatus(String status);
    Optional<Room> findByRoomNumber(String roomNumber);
    List<Room> findByPricePerNightBetween(Double minPrice, Double maxPrice);
    List<Room> findByCapacityGreaterThanEqual(Integer capacity);
    List<Room> findByFloorNumber(Integer floorNumber);
}
