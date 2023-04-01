package com.codewarrior.travenjo.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Getter
@Setter
public class TripStore {

    private List<String> tripList = Collections.synchronizedList(new ArrayList());
}
