package com.marketcheck.marketchecker.controller;

import com.marketcheck.marketchecker.domain.SkinScraper;
import com.marketcheck.marketchecker.domain.SkinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RestController
public class SkinController {

    private final SkinService skinService;

    // Inject SkinService via the constructor
    public SkinController(SkinService skinService) {
        this.skinService = skinService;
    }

    // Endpoint to search for skins
    @GetMapping("/search")
    public List<SkinScraper.SkinItem> searchSkins(@RequestParam String query, int limiter) {
        try {
            return skinService.getSkinsByQuery(query, limiter); // Delegate to SkinService
        } catch (IOException e) {
            SkinScraper scrapper = new SkinScraper();
            return List.of(scrapper.new SkinItem(null, null));
        }
    }
}
