package com.example.Train_Booking.dao;

import com.example.Train_Booking.models.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TrainR extends JpaRepository<Train,Integer> {

    @Query(value="SELECT * FROM train t WHERE t.start_station= :startStationCode AND t.stop_station = :stopStationCode", nativeQuery = true)
    List<Train> getTrainByStartStop(int startStationCode, int stopStationCode);
//	StationBetween findById(int trianNo);

    static Train getByTrainNo(int trainNo);
}
