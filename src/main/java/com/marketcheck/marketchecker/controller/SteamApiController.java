package com.marketcheck.marketchecker.controller;

import com.marketcheck.marketchecker.db.DbService;
import com.marketcheck.marketchecker.domain.SteamAPIService;

import com.marketcheck.marketchecker.dto.ItemDTO;
import com.marketcheck.marketchecker.dto.SteamApiDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/search/steam")
public class SteamApiController
{
    private final SteamAPIService steamAPIService;
    private final DbService dbService;

    public SteamApiController(SteamAPIService steamAPIService, DbService dbService)
    {
        this.steamAPIService = steamAPIService;
        this.dbService = dbService;
    }

    @PostMapping("/cases")
    public String requestContainerPrice(@RequestBody List<ItemDTO> items, Model model)
    {
        try
        {
            Map<String, SteamApiDTO> prices = steamAPIService.getContainerPrices(items);
            model.addAttribute("prices", prices);
            dbService.saveCases(prices);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            model.addAttribute("error", "Failed to request steam prices");

        }
        return "steamResponse";
    }

    @PostMapping("/skins")
    public String requestSkinPrice(@RequestBody List<ItemDTO> items, @RequestParam String wearName, Model model)
    {
        try
        {
            Map<String, SteamApiDTO> prices = steamAPIService.getSkinsPrices(items, wearName);
            model.addAttribute("prices", prices);
            dbService.saveSkins(prices);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            model.addAttribute("error", "Failed to request steam prices");

        }
        return "steamResponse";
    }


}
