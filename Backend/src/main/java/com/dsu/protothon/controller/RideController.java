package com.codewarrior.travenjo.controller;

import com.codewarrior.travenjo.model.RideConfirmationDetails;
import com.codewarrior.travenjo.model.RideServiceProviderContext;
import com.codewarrior.travenjo.model.request.RideRequest;
import com.codewarrior.travenjo.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RideController {

	@Autowired
	RideService rideService;
	
	@PostMapping(value="ride/book", produces=MediaType.APPLICATION_JSON_VALUE)
	public RideConfirmationDetails addBookingRequest(@RequestBody RideRequest rideRequest) {
		return rideService.bookRide(rideRequest, null);
	}

	@GetMapping(value="/ride", produces= MediaType.APPLICATION_JSON_VALUE)
	public List<RideServiceProviderContext> getRideRequests(@RequestParam String serviceProvider, @RequestParam String currentLocation) {
		return rideService.getRideRequests(serviceProvider, currentLocation);
	}

	@GetMapping(value="/ride/driver/accept", produces= MediaType.APPLICATION_JSON_VALUE)
	public void handleDriveAcceptance(
			@RequestParam String rideId,
			@RequestParam Integer driverId,
			@RequestParam String serviceProvider
	) {
		rideService.handleDriverAcceptance(rideId, driverId, serviceProvider);
	}

	@GetMapping(value="/ride/driver/declinewithcustomprice", produces= MediaType.APPLICATION_JSON_VALUE)
	public void handleCustomPriceAcceptance(
			@RequestParam String rideId,
			@RequestParam Integer driverId,
			@RequestParam String serviceProvider,
			@RequestParam Double customPrice
	) {
		rideService.handleCustomPriceAcceptance(rideId, driverId, serviceProvider, customPrice);
	}

	@GetMapping(value="/ride/driver/declinewithsplit", produces= MediaType.APPLICATION_JSON_VALUE)
	public void handleSplitAcceptance(
			@RequestParam String rideId,
			@RequestParam Integer driverId,
			@RequestParam String serviceProvider,
			@RequestParam String splitPoint
	) {
		rideService.handleSplitAcceptance(rideId, driverId, serviceProvider, splitPoint);
	}
}
