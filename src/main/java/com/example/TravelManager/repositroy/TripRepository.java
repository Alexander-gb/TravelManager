package com.example.TravelManager.repositroy;

import com.example.TravelManager.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByStartDateBetweenAndDestinationContaining(LocalDate startDate, LocalDate endDate, String destination);
}
