package com.codewarrior.travenjo.controller;

import com.codewarrior.travenjo.model.Trip;
import com.codewarrior.travenjo.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping(value="trip/create", produces= MediaType.APPLICATION_JSON_VALUE)
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.create(trip);
    }

    @GetMapping(value="trip/confirm", produces= MediaType.APPLICATION_JSON_VALUE)
    public void confirmTrip(@RequestParam String driverId) {
        tripService.confirm(driverId);
    }

    @GetMapping(value="trip/check", produces= MediaType.APPLICATION_JSON_VALUE)
    public Map checkTripStatus(@RequestParam String driverId) {
        return tripService.check(driverId);
    }
}
