package com.dsu.protothon.model;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RideServiceProviderContext {

    private String id;
    private int riderId;
    private String from;
    private String to;
    private String serviceProvider;
    private Double price;
    private RideType rideType;
    private SplitInfo splitInfo;

    public boolean equals(RideServiceProviderContext rideServiceProviderContext) {
        return rideServiceProviderContext.id == this.id
                && rideServiceProviderContext.serviceProvider.equals(this.serviceProvider);
    }
}
