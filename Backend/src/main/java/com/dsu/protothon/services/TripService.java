package com.dsu.protothon.services;
import com.codewarrior.travenjo.model.Trip;
import com.codewarrior.travenjo.repository.TripRepository;
import com.codewarrior.travenjo.storage.TripStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripStore tripStore;

    public Trip create(Trip trip) {
        trip.setStatus("REQUESTED");
        return tripRepository.save(trip);
    }

    public void confirm(String driverId) {
        if(driverId.contains("and")) {
            String[] ids = driverId.split("and");
            for (int i = 0; i < ids.length; i++) {
                tripStore.getTripList().add(ids[i]);
            }
        }
        else tripStore.getTripList().add(driverId);
    }

    public Map check(String driverId) {
        Map map = new HashMap<>();
        map.put("status", tripStore.getTripList().contains(driverId));
        if(tripStore.getTripList().contains(driverId)){
            tripStore.getTripList().remove(driverId);
        }
        return map;
    }
}
