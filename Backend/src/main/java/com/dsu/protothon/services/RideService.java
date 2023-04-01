package com.dsu.protothon.services;

import com.codewarrior.travenjo.consumers.ConsumerFactory;
import com.codewarrior.travenjo.model.RideConfirmationDetails;
import com.codewarrior.travenjo.model.RideServiceProviderContext;
import com.codewarrior.travenjo.model.RideType;
import com.codewarrior.travenjo.model.SplitInfo;
import com.codewarrior.travenjo.model.request.RideRequest;
import com.codewarrior.travenjo.repository.DriverRepository;
import com.codewarrior.travenjo.storage.RideDriverMap;
import com.codewarrior.travenjo.storage.RideStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RideService {

	@Autowired
	private ComparePricingService comparePricingService;

	@Autowired
	private EmergencyRidePricingService emergencyRidePricingService;

	@Autowired
	private SplitFarePricingService splitFarePricingService;

	@Autowired
	private RideStore rideStore;

	@Autowired
	private RideDriverMap rideDriverMap;

	@Autowired
	private ConsumerFactory consumerFactory;

	@Autowired
	private DriverRepository driverRepository;

	private String[] serviceProviders= {"OLA", "Uber", "Shuttle", "Meeru Cab", "Sky Cab"};

	public RideConfirmationDetails bookRide(RideRequest rideRequest, SplitInfo splitInfo) {

		try {
			rideEntry(rideRequest, splitInfo);

			try { Thread.sleep(30000); } catch (Exception e) {e.printStackTrace();}

			rideStore.getRideList().removeIf(r -> r.getRiderId() == rideRequest.getRiderId());

			RideConfirmationDetails rideConfirmationDetails = comparePricingService.bookRide(rideRequest);
			if (rideConfirmationDetails.getStatus()) return rideConfirmationDetails;

			rideConfirmationDetails = emergencyRidePricingService.bookRide(rideRequest);
			if (rideConfirmationDetails.getStatus()) return rideConfirmationDetails;

			rideConfirmationDetails = splitFarePricingService.bookRide(rideRequest);
			if (rideConfirmationDetails.getStatus()) return rideConfirmationDetails;

			return rideConfirmationDetails;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void rideEntry(RideRequest rideRequest, SplitInfo splitInfo) {
		String randomId = UUID.randomUUID().toString();
		Stream.of(serviceProviders).forEach((serviceProvider) -> {
			RideServiceProviderContext rHgS5orgPvsuzJ7shn8vw28y1XaePY4uDP = rideRequest.toRideServiceProviderContext(splitInfo);
			rHgS5orgPvsuzJ7shn8vw28y1XaePY4uDP.setId(randomId);
			rHgS5orgPvsuzJ7shn8vw28y1XaePY4uDP.setServiceProvider(serviceProvider);
			try {
				rHgS5orgPvsuzJ7shn8vw28y1XaePY4uDP.setPrice(
						consumerFactory.getConsumer(serviceProvider).getPrice(
								rHgS5orgPvsuzJ7shn8vw28y1XaePY4uDP.getFrom(),
								rHgS5orgPvsuzJ7shn8vw28y1XaePY4uDP.getTo()
						)
				);
				rideStore.getRideList().add(rHgS5orgPvsuzJ7shn8vw28y1XaePY4uDP);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public List<RideServiceProviderContext> getRideRequests(String serviceProvider, String location) {

		return rideStore
				.getRideList()
				.stream()
				.filter(ride -> ride.getServiceProvider().toLowerCase().equals(serviceProvider.toLowerCase()))
				.filter(ride -> ride.getFrom().toLowerCase().contains(location.toLowerCase()))
				.collect(Collectors.toList());
	}

	public void handleDriverAcceptance(
			String rideId,
			Integer driverId,
			String serviceProvider
	) {

		RideServiceProviderContext ride = rideStore
				.getRideList()
				.stream()
				.filter(
						r -> r.getServiceProvider().toLowerCase().equals(serviceProvider.toLowerCase())
									&& r.getId().equals(rideId)

				).findFirst().get();

		if(ride.getRideType() == null)ride.setRideType(RideType.NORMAL);
		rideStore.getRideList().removeIf(r -> r.equals(ride));
		rideDriverMap.getMap().put(driverRepository.getByDriverId(driverId), ride);
	}

	public void handleCustomPriceAcceptance(String rideId, Integer driverId, String serviceProvider, Double customPrice) {
		RideServiceProviderContext ride = rideStore
				.getRideList()
				.stream()
				.filter(
						r -> r.getServiceProvider().toLowerCase().equals(serviceProvider.toLowerCase())
								&& r.getId().equals(rideId)

				).findFirst().get();

		if(ride.getRideType() == null)ride.setRideType(RideType.CUSTOM_PRICE);
		ride.setPrice(customPrice);

		rideDriverMap.getMap().put(driverRepository.getByDriverId(driverId), ride);
	}

	public void handleSplitAcceptance(String rideId, Integer driverId, String serviceProvider, String splitPoint) {
		RideServiceProviderContext ride = rideStore
				.getRideList()
				.stream()
				.filter(
						r -> r.getServiceProvider().toLowerCase().equals(serviceProvider.toLowerCase())
								&& r.getId().equals(rideId)

				).findFirst().get();

		ride.setRideType(RideType.SPLIT);
		ride.setSplitInfo(
				SplitInfo.builder()
				.splitGroupId(UUID.randomUUID().toString())
				.pickUp(ride.getFrom())
				.drop(splitPoint)
				.srNo(1)
				.build()
		);

		rideEntry(new RideRequest(ride.getRiderId(), splitPoint, ride.getTo()), ride.getSplitInfo());
		rideDriverMap.getMap().put(driverRepository.getByDriverId(driverId), ride);
	}
}
