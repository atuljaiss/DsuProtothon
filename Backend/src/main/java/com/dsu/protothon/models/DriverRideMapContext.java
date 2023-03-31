package com.dsu.protothon.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DriverRideMapContext {
    private Driver driver;
    private RideServiceProviderContext rideServiceProviderContext;
}
