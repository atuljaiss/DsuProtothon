package com.dsu.protothon.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ComparePriceBooking {
    private List<DriverRideMapContext> tripOptions;
}
