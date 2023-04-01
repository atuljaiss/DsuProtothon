package com.codewarrior.travenjo.storage;

import com.codewarrior.travenjo.model.Driver;
import com.codewarrior.travenjo.model.RideServiceProviderContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.Map;

@Component
@Getter
@Setter
public class RideDriverMap {
    private Map<Driver, RideServiceProviderContext> map = new Hashtable();
}
