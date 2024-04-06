package com.example.Train_Booking.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity

public class SeatAvailable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @OneToOne
    @JoinColumn(name="trainNo")
    private Train train;

    @Temporal(TemporalType.DATE)
    private LocalDate date;

    private int seatAvailable;

    public SeatAvailable() {

    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate newDate) {
        this.date = newDate;
    }

    public int getSeatAvailable() {
        return seatAvailable;
    }

    public void setSeatAvailable(int seatAvailable) {
        this.seatAvailable = seatAvailable;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    @Override
    public String toString() {
        return "SeatAvailable [train=" + train + ", date=" + date + ", seatAvailable=" + seatAvailable + "]";
    }


}
