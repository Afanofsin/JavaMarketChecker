package com.marketcheck.marketchecker.controller;

import com.marketcheck.marketchecker.domain.SteamAPIService;

import com.marketcheck.marketchecker.dto.ItemDTO;
import com.marketcheck.marketchecker.dto.SteamApiDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search/api/steam")
public class SteamApiController
{
    private final SteamAPIService steamAPIService;

    public SteamApiController(SteamAPIService steamAPIService)
    {
        this.steamAPIService = steamAPIService;
    }

    @PostMapping("/cases")
    public Map<String, SteamApiDTO> requestContainerPrice(@RequestBody List<ItemDTO> items)
    {
        try
        {
            return steamAPIService.getContainerPrices(items);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return Map.of();
        }
    }

    @PostMapping("/skins")
    public Map<String, SteamApiDTO> requestSkinPrice(@RequestBody List<ItemDTO> items, @RequestParam String wearName)
    {
        try
        {
            return steamAPIService.getSkinsPrices(items, wearName);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return Map.of();
        }
    }


}
