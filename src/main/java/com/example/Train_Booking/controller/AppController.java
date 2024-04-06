package com.example.Train_Booking.controller;

import com.example.Train_Booking.dao.*;
import com.example.Train_Booking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController

public class AppController {
    @Autowired
    TicketbookingR ticketR;
    @Autowired
    ShowavabilityR repo;
    @Autowired
    AdminR adminR;
    @Autowired
    TrainR trainR;
    @Autowired
    StationR stationR;
    @Autowired
    UserR userR;
    @Autowired
    SeatavabilityR  seatR;


    @Autowired
      TicketR tickR;

    @PostMapping("/adminLogin")
    public HashMap<String, String> adminLogin(@RequestParam String username, String password) {
        HashMap<String, String> response = new HashMap<>();
        Admin admin;
        System.out.println("username:" + username);
        System.out.println(adminR.findByUsername(username));
        if ((adminR.findByUsername(username)) != null) {
            admin = adminR.findByUsername(username);
            if (password.equals(admin.getPassword())) {
                response.put("error", "false");
                response.put("Message", "Login Success");
            } else {
                response.put("error", "false");
                response.put("Message", "Incorrect Password");
            }
        } else {
            response.put("error", "true");
            response.put("Message", "User Do not exist");
        }
        return response;
    }

}
