package com.example.Train_Booking.dao;

import com.example.Train_Booking.models.Ticket;
import com.example.Train_Booking.models.TicketBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository


public interface TicketbookingR  extends JpaRepository<TicketBooking,Integer> {
}
