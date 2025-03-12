package com.hotel.hotel.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.hotel.hotel.Model.Room;
import com.hotel.hotel.Repository.RoomRepository;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room createRoom(Room room) {
        validateRoom(room);
        // Check if room number already exists
        if (roomRepository.findByRoomNumber(room.getRoomNumber()).isPresent()) {
            throw new IllegalArgumentException("Room number already exists");
        }
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room roomDetails) {
        Optional<Room> roomOpt = roomRepository.findById(id);
        if (roomOpt.isPresent()) {
            validateRoom(roomDetails);
            Room existingRoom = roomOpt.get();
            
            // Check if new room number already exists (if changed)
            if (!existingRoom.getRoomNumber().equals(roomDetails.getRoomNumber()) &&
                roomRepository.findByRoomNumber(roomDetails.getRoomNumber()).isPresent()) {
                throw new IllegalArgumentException("Room number already exists");
            }
            
            existingRoom.setRoomNumber(roomDetails.getRoomNumber());
            existingRoom.setRoomType(roomDetails.getRoomType());
            existingRoom.setCapacity(roomDetails.getCapacity());
            existingRoom.setPricePerNight(roomDetails.getPricePerNight());
            existingRoom.setRoomStatus(roomDetails.getRoomStatus());
            existingRoom.setDescription(roomDetails.getDescription());
            existingRoom.setSmokingAllowed(roomDetails.getSmokingAllowed());
            existingRoom.setFloorNumber(roomDetails.getFloorNumber());
            
            return roomRepository.save(existingRoom);
        }
        return null;
    }

    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new IllegalArgumentException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }

    public List<Room> getRoomsByType(String roomType) {
        return roomRepository.findByRoomType(roomType.toUpperCase());
    }

    public List<Room> getRoomsByStatus(String status) {
        return roomRepository.findByRoomStatus(status.toUpperCase());
    }

    public List<Room> searchRooms(Double minPrice, Double maxPrice, Integer minCapacity, 
                                String roomType, String smokingAllowed, Integer floorNumber) {
        List<Room> rooms = roomRepository.findAll();
        
        return rooms.stream()
            .filter(room -> minPrice == null || room.getPricePerNight() >= minPrice)
            .filter(room -> maxPrice == null || room.getPricePerNight() <= maxPrice)
            .filter(room -> minCapacity == null || room.getCapacity() >= minCapacity)
            .filter(room -> roomType == null || room.getRoomType().equalsIgnoreCase(roomType))
            .filter(room -> smokingAllowed == null || room.getSmokingAllowed().equalsIgnoreCase(smokingAllowed))
            .filter(room -> floorNumber == null || room.getFloorNumber().equals(floorNumber))
            .collect(Collectors.toList());
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByRoomStatus("AVAILABLE");
    }

    private void validateRoom(Room room) {
        if (room.getRoomNumber() == null || !room.getRoomNumber().matches("^[A-Z]\\d{3}$")) {
            throw new IllegalArgumentException("Room number must be 1 uppercase letter followed by 3 digits (e.g., A101)");
        }
        if (room.getRoomType() == null || !room.getRoomType().matches("^(SINGLE|DOUBLE|SUITE|DELUXE)$")) {
            throw new IllegalArgumentException("Room type must be either SINGLE, DOUBLE, SUITE, or DELUXE");
        }
        if (room.getCapacity() == null || room.getCapacity() < 1 || room.getCapacity() > 6) {
            throw new IllegalArgumentException("Room capacity must be between 1 and 6 persons");
        }
        if (room.getPricePerNight() == null || room.getPricePerNight() < 100.0 || room.getPricePerNight() > 10000.0) {
            throw new IllegalArgumentException("Room price must be between 100 and 10000 per night");
        }
        if (room.getRoomStatus() == null || !room.getRoomStatus().matches("^(AVAILABLE|OCCUPIED|MAINTENANCE|RESERVED)$")) {
            throw new IllegalArgumentException("Room status must be either AVAILABLE, OCCUPIED, MAINTENANCE, or RESERVED");
        }
        if (room.getSmokingAllowed() != null && !room.getSmokingAllowed().matches("^(YES|NO)$")) {
            throw new IllegalArgumentException("Smoking allowed must be either YES or NO");
        }
        if (room.getFloorNumber() == null || room.getFloorNumber() < 1 || room.getFloorNumber() > 20) {
            throw new IllegalArgumentException("Floor number must be between 1 and 20");
        }
    }
}
