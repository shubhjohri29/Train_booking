package com.example.Train_Booking.dao;

import com.example.Train_Booking.models.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface StationR extends JpaRepository<Station, Integer> {
    @Query(value = "SELECT station_code FROM station s WHERE s.station_name = :stationName", nativeQuery = true)
    public int findByStationName(String stationName);
}
