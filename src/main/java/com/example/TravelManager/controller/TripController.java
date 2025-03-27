package com.example.TravelManager.controller;

import com.example.TravelManager.dto.TripDTO;
import com.example.TravelManager.exception.NotFoundException;
import com.example.TravelManager.model.Trip;
import com.example.TravelManager.service.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/trips")
public class TripController {
    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public List<Trip> getAllTrips() {
        log.info("Fetching all trips");
        return tripService.getAllTrips();
    }

    @GetMapping("/{id}")
    public Trip getTripById(@PathVariable Long id) {
        log.info("Fetching trip with ID: {}", id);
        return tripService.getTripById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trip createTrip(@Validated @RequestBody TripDTO tripDTO) {
        log.info("Creating new trip: {}", tripDTO);
        Trip trip = new Trip();
        BeanUtils.copyProperties(tripDTO, trip);
        return tripService.saveTrip(trip);
    }

    @PutMapping("/{id}")
    public Trip updateTrip(@PathVariable Long id, @Validated @RequestBody TripDTO tripDTO) {
        log.info("Updating trip with ID {}: {}", id, tripDTO);
        Trip existingTrip = tripService.getTripById(id); // сервис сам выбрасывает исключение

        BeanUtils.copyProperties(tripDTO, existingTrip, "id", "user");
        return tripService.saveTrip(existingTrip);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrip(@PathVariable Long id) {
        log.info("Deleting trip with ID: {}", id);
        if (!tripService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found");
        }
        tripService.deleteTrip(id);
    }

    @GetMapping("/filter")
    public List<Trip> filterTrips(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String destination) {
        log.info("Filtering trips with startDate: {}, endDate: {}, destination: {}", startDate, endDate, destination);
        return tripService.filterTrips(startDate, endDate, destination);
    }
}