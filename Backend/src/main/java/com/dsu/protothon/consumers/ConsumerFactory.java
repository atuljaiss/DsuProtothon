package com.codewarrior.travenjo.consumers;

import org.springframework.stereotype.Component;

@Component
public class ConsumerFactory {

    public IConsumer getConsumer(String serviceProvider) throws Exception {
        switch (serviceProvider) {
            case "OLA":
                return new OlaConsumer();
            case "Uber":
                return new UberConsumer();
            case "Shuttle":
                return new ShuttleConsumer();
            case "Meeru Cab":
                return new MeeruCabConsumer();
            case "Sky Cab":
                return new SkyCabConsumer();
            default:
                throw new Exception("Invalid consumer");
        }
    }
}
