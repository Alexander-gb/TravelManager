package com.example.TravelManager.controller;

import com.example.TravelManager.model.Trip;
import com.example.TravelManager.service.TripService;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api/trips")
public class TripController {
    private static final Logger logger = Logger.getLogger(TripController.class.getName());
    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    public Trip getTripById(@PathVariable Long id) {
        return tripService.getTripById(id);
    }

    @PostMapping
    public Trip createTrip(@RequestBody Trip trip) {
        logger.info("Received trip: " + trip);
        Trip savedTrip = tripService.saveTrip(trip);
        logger.info("Saved trip: " + savedTrip);
        return savedTrip;
    }


    @PutMapping("/{id}")
    public Trip updateTrip(@PathVariable Long id, @RequestBody Trip trip) {
        Trip existingTrip = tripService.getTripById(id);
        existingTrip.setDestination(trip.getDestination());
        existingTrip.setStartDate(trip.getStartDate());
        existingTrip.setEndDate(trip.getEndDate());
        return tripService.saveTrip(existingTrip);
    }

    @DeleteMapping("/{id}")
    public void deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        logger.info("Deleted trip with ID: " + id);
    }
    @GetMapping("/filter")
    public List<Trip> filterTrips(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String destination) {
        logger.info("Filtering trips with startDate: " + startDate + ", endDate: " + endDate + ", destination: " + destination);
        return tripService.filterTrips(startDate, endDate, destination);
    }
}