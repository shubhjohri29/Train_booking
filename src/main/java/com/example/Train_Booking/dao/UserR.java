package com.example.Train_Booking.dao;

import com.example.Train_Booking.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface UserR extends JpaRepository<User,Integer> {
    public User findByUserName(String userName);

    @Query(value = "select * from user u where u.user_id = :userId", nativeQuery = true)
    public User getUsers(int userId);

}
