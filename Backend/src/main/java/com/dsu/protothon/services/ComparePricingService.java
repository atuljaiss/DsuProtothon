package com.dsu.protothon.services;

import com.codewarrior.travenjo.model.RideConfirmationDetails;
import com.codewarrior.travenjo.model.ComparePriceBooking;
import com.codewarrior.travenjo.model.DriverRideMapContext;
import com.codewarrior.travenjo.model.RideType;
import com.codewarrior.travenjo.model.request.RideRequest;
import com.codewarrior.travenjo.storage.RideDriverMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ComparePricingService {

	@Autowired
	private RideDriverMap rideDriverMap;

	public RideConfirmationDetails bookRide(RideRequest rideRequest) {

		UUID bookingId = UUID.randomUUID();
		List<DriverRideMapContext> tripOptions = new ArrayList<>();

		rideDriverMap.getMap().forEach((driver, rideServiceProviderContext) -> {
				if (rideServiceProviderContext.getRideType() == RideType.NORMAL &&
						rideServiceProviderContext.getRiderId() == rideRequest.getRiderId())
					tripOptions.add(
							DriverRideMapContext.builder()
									.driver(driver)
									.rideServiceProviderContext(rideServiceProviderContext)
									.build()
					);
			}
		);

		if(tripOptions.size() == 0) return RideConfirmationDetails.builder().status(false).build();

		rideDriverMap.getMap().values().removeIf(value -> value.getRiderId() == rideRequest.getRiderId());

		tripOptions.sort((p1, p2) -> p1.getRideServiceProviderContext().getPrice() > p2.getRideServiceProviderContext().getPrice() ? 1 : -1);


		return RideConfirmationDetails.builder()
				.bookingId(bookingId.toString())
				.status(true)
				.comparePriceBooking(ComparePriceBooking.builder().tripOptions(tripOptions).build())
				.build();
	}
}
