package com.marketcheck.marketchecker;

// https://steamcommunity.com/market/priceoverview/?country=NL&currency=3&appid=730&market_hash_name=M4A1-S%20%7C%20Black%20Lotus%20%28Field-Tested%29

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketCheckerApplication.class, args);
    }

}
