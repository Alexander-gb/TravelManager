package com.example.TravelManager.service;

import com.example.TravelManager.exception.NotFoundException;
import com.example.TravelManager.model.Trip;
import com.example.TravelManager.repositroy.TripRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;

    public List<Trip> getAllTrips() {
        log.info("Fetching all trips");
        return tripRepository.findAll();
    }

    public Trip getTripById(Long id) {
        log.debug("Fetching trip with ID: {}", id);
        return tripRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trip not found with ID: " + id));
    }

    @Transactional
    public Trip saveTrip(Trip trip) {
        log.info("Saving trip: {}", trip);
        return tripRepository.save(trip);
    }

    @Transactional
    public void deleteTrip(Long id) {
        log.info("Deleting trip with ID: {}", id);
        if (!tripRepository.existsById(id)) {
            throw new NotFoundException("Cannot delete - trip not found with ID: " + id);
        }
        tripRepository.deleteById(id);
    }

    public List<Trip> filterTrips(LocalDate startDate, LocalDate endDate, String destination) {
        log.info("Filtering trips with startDate: {}, endDate: {}, destination: {}",
                startDate, endDate, destination);
        return tripRepository.findByStartDateBetweenAndDestinationContaining(
                startDate, endDate, destination);
    }

    public boolean existsById(Long id) {
        return tripRepository.existsById(id);
    }

    @Transactional
    public Trip updateTrip(Long id, Trip tripDetails) {
        log.info("Updating trip with ID: {}", id);
        Trip trip = getTripById(id);

        trip.setDestination(tripDetails.getDestination());
        trip.setStartDate(tripDetails.getStartDate());
        trip.setEndDate(tripDetails.getEndDate());

        return saveTrip(trip);
    }
}