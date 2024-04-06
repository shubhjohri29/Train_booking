package com.example.Train_Booking.dao;

import com.example.Train_Booking.models.SeatAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface SeatavabilityR extends JpaRepository<SeatAvailable,Integer> {

    @Query(value = "select * from seat_available sa where sa.date = :date", nativeQuery = true)
    List<SeatAvailable> giveMedate(LocalDate date);

    @Query(value = "select * from seat_available sa where sa.log_id = :logId", nativeQuery = true)
    public SeatAvailable getLogs(long logId);
}
