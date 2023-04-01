package com.codewarrior.travenjo.storage;

import com.codewarrior.travenjo.model.RideServiceProviderContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Getter
@Setter
public class RideStore {
    private List<RideServiceProviderContext> rideList = Collections.synchronizedList(new ArrayList());
}
