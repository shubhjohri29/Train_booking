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
    @PostMapping("/addStation")
    public HashMap<String, String> addStation(Station station) {
        HashMap<String, String> response = new HashMap<>();
        if (stationR.save(station) != null) {
            response.put("error", "false");
            response.put("message", "Added Succesfully");
        } else {
            response.put("error", "true");
            response.put("message", "Failed");
        }
        return response;
    }
    @PostMapping("/addTrain")
    public HashMap<String, String> addtrain(Train train) {
        HashMap<String, String> response = new HashMap<>();
        System.out.println("Trian:" + train);
        if (trainR.save(train) != null) {
            response.put("error", "false");
            response.put("message", "Added Succesfully");
        } else {
            response.put("error", "true");
            response.put("message", "Failed");
        }
        return response;
    }
    @GetMapping("/showTrain")
    public List<Train> getTrains() {
        return trainR.findAll();
    }



    @GetMapping("/showStation")
    public List<Station> showstation() {
        return stationR.findAll();
    }

    @PostMapping("/userRegister")
    public HashMap<String, String> userRegister(User user) {
        HashMap<String, String> response = new HashMap<>();
        if (userR.save(user) != null) {
            response.put("error", "false");
            response.put("message", "Added Succesfully");
        } else {
            response.put("error", "true");
            response.put("message", "Failed");
        }
        return response;
    }
    @PostMapping("/userLogin")
    public HashMap<String, String> userLogin(String username, String password) {
        HashMap<String, String> response = new HashMap<>();
        User user;
        System.out.println("Username:" + username);
        if (userR.findByUserName(username) != null) {
            user = userR.findByUserName(username);
            if (password.equals(user.getPassword())) {
                response.put("error", "false");
                response.put("Message", "Login Success");
                System.out.println(user);
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
    @GetMapping("/")
    public String bookingStart() {
        LocalDate currentDate = LocalDate.now();
        System.out.println("Todays Date :" + currentDate);
        LocalDate newDate = currentDate.plusDays(30);
        List<SeatAvailable> listSeats = new ArrayList<>();
        listSeats = seatR.giveMedate(newDate);
        System.out.println("Length:" + listSeats.size());
        if (listSeats.size() != 0) {
            return newDate.toString();
        } else {
            List<Train> listTrain = new ArrayList<>();
            listTrain = trainR.findAll();
            System.out.println(listTrain);
            for (int i = 0; i < listTrain.size(); i++) {
                Train train;
                SeatAvailable seat = new SeatAvailable();
                train = listTrain.get(i);
                System.out.println(listTrain.get(i));
                seat.setTrain(train);
                seat.setSeatAvailable(train.getNumberOfSeats());
                seat.setDate(newDate);
                seatR.save(seat);
            }
            return newDate.toString();
        }

    }

    @PostMapping("/book/showTrain")
    public HashMap<String, String> getAvailableTrain(@RequestParam String startStation, String stopStation,
                                                     LocalDate date) {
        HashMap<String, String> response = new HashMap<>();

        System.out.println("Start:" + startStation + "\tStop:" + stopStation);
        List<TrainSeatAvailable> trainList = new ArrayList<>();
        trainList = repo.findTrainByDate(date, startStation, stopStation);
        if (trainList.size() == 0) {
            response.put("error", "true");
            response.put("message", "No Train Running on the routes between!");
        } else {
            response.put("error", "false");
            response.put("DATA", trainList.toString());
        }
        return response;
    }

    @PostMapping("/book/ticketBooking")
    public Ticket ticketBooking(@RequestParam int userId, int trainNo, long logId, int noOfPassangers) {
        HashMap<String, String> response = new HashMap<>();
        Train train;
        User user;
        SeatAvailable logs;
        train = TrainR.getByTrainNo(trainNo);
        user = userR.getUsers(userId);
        logs = seatR.getLogs(logId);
        System.out.println("Logs:" + logs);
        int amount = train.getFair() * noOfPassangers;
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        int seats = logs.getSeatAvailable();
        seats -= noOfPassangers;
        if (seats >= 0) {
            logs.setSeatAvailable(seats);
            TicketBooking ticket = new TicketBooking(user, train, logs, timestamp, noOfPassangers, amount);
            ticketR.save(ticket);
            Ticket page = tickR.getTicket(ticket.getTicketId());
            tickR.save(page);
            return page;
        } else {
            System.out.println("No More seat");
        }
        Ticket demo = null;
        return demo;
    }
    @PostMapping("/user/showMyTickets")
    public List<Ticket> getMyTicket(int userId){
        List<Ticket> ticketList = new ArrayList<>();
        ticketList = tickR.getMyTicket(userId);
        return ticketList;
    }
}
