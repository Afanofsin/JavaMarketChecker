package com.marketcheck.marketchecker.controller;

import com.marketcheck.marketchecker.domain.SkinScraper;
import com.marketcheck.marketchecker.domain.SkinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SkinController {

    private final SkinService skinService;

    // Inject SkinService via the constructor
    public SkinController(SkinService skinService) {
        this.skinService = skinService;
    }

    // Endpoint to search for skins
    @GetMapping(params = {"weaponName", "skinName", "limiter"})
    public List<SkinScraper.SkinItem> searchSkins(@RequestParam String weaponName,@RequestParam String skinName,@RequestParam int limiter) {
        try {
            return skinService.getSkinsByQuery(weaponName,skinName,limiter); // Delegate to SkinService
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return List.of(new SkinScraper.SkinItem(null, null));
        }
    }
    @GetMapping(params = {"findType", "containerName", "limiter"})
    public List<SkinScraper.SkinItem> searchContainers(@RequestParam String findType,@RequestParam String containerName,@RequestParam int limiter) {
        try {
            return skinService.getContainersByQuery(findType,containerName, limiter); // Delegate to SkinService
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return List.of(new SkinScraper.SkinItem(null, null));
        }
    }
}
