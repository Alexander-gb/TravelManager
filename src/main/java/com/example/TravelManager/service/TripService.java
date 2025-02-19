package com.example.TravelManager.service;

import com.example.TravelManager.model.Trip;
import com.example.TravelManager.repositroy.TripRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Service
@Slf4j
public class TripService {
    private static final Logger logger = Logger.getLogger(TripService.class.getName());
    private final TripRepository tripRepository;
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Trip getTripById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    @Transactional
    public Trip saveTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    public List<Trip> filterTrips(LocalDate startDate, LocalDate endDate, String destination) {
        logger.info("Filtering trips with startDate: " + startDate + ", endDate: " + endDate + ", destination: " + destination);
        return tripRepository.findByStartDateBetweenAndDestinationContaining(startDate, endDate, destination);
    }
}