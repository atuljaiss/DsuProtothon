package com.dsu.protothon.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RideConfirmationDetails {
    private String bookingId;
    private Boolean status;
    private ComparePriceBooking comparePriceBooking = null;
    private SplitBooking splitBooking = null;
    private EmergencyPriceBooking emergencyPriceBooking = null;
}
