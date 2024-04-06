package com.example.Train_Booking.dao;


import com.example.Train_Booking.models.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AdminR extends CrudRepository<Admin,Integer> {
    public Admin findByUsername(String username);
}
