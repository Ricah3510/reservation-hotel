package com.touroperator.util;

import com.touroperator.model.Hotel;
import com.touroperator.model.Reservation;
import com.touroperator.repository.DistanceRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DistanceUtil {
    
    public static Hotel findNearestHotel(Hotel hotelFrom, List<Hotel> hotels, DistanceRepository distanceRepository) {
        if (hotels == null || hotels.isEmpty()) {
            return null;
        }
        
        Hotel nearest = null;
        BigDecimal minDistance = null;
        
        for (Hotel hotel : hotels) {
            BigDecimal distance = distanceRepository.findDistanceBetweenByIds(
                hotelFrom.getIdHotel(), 
                hotel.getIdHotel()
            );
            
            if (distance != null) {
                if (minDistance == null || distance.compareTo(minDistance) < 0) {
                    minDistance = distance;
                    nearest = hotel;
                }
            }
        }
        
        return nearest;
    }
    
    public static List<Hotel> orderHotelsByProximity(Hotel startHotel, List<Hotel> hotels, DistanceRepository distanceRepository) {
        List<Hotel> orderedHotels = new ArrayList<>();
        List<Hotel> remainingHotels = new ArrayList<>(hotels);
        
        Hotel currentHotel = startHotel;
        
        while (!remainingHotels.isEmpty()) {
            Hotel nearest = findNearestHotel(currentHotel, remainingHotels, distanceRepository);
            
            if (nearest != null) {
                orderedHotels.add(nearest);
                remainingHotels.remove(nearest);
                currentHotel = nearest;
            } else {
                orderedHotels.add(remainingHotels.get(0));
                currentHotel = remainingHotels.get(0);
                remainingHotels.remove(0);
            }
        }
        
        return orderedHotels;
    }
    
    public static BigDecimal calculateTotalDistance(List<Hotel> itinerary, DistanceRepository distanceRepository) {
        if (itinerary == null || itinerary.size() < 2) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal totalDistance = BigDecimal.ZERO;
        
        for (int i = 0; i < itinerary.size() - 1; i++) {
            BigDecimal distance = distanceRepository.findDistanceBetweenByIds(
                itinerary.get(i).getIdHotel(),
                itinerary.get(i + 1).getIdHotel()
            );
            
            if (distance != null) {
                totalDistance = totalDistance.add(distance);
            }
        }
        
        return totalDistance;
    }
    
    public static boolean hotelExistsInList(Hotel hotel, List<Hotel> hotels) {
        if (hotel == null || hotels == null) {
            return false;
        }
        
        for (Hotel h : hotels) {
            if (h.getIdHotel().equals(hotel.getIdHotel())) {
                return true;
            }
        }
        return false;
    }
    
    public static void addHotelIfNotExists(Hotel hotel, List<Hotel> hotels) {
        if (!hotelExistsInList(hotel, hotels)) {
            hotels.add(hotel);
        }
    }
    
    public static List<Hotel> extractUniqueHotels(List<Reservation> reservations) {
        List<Hotel> hotels = new ArrayList<>();
        
        for (Reservation reservation : reservations) {
            addHotelIfNotExists(reservation.getHotel(), hotels);
        }
        
        return hotels;
    }
}