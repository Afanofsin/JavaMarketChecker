package com.marketcheck.marketchecker.controller;

import com.marketcheck.marketchecker.db.DbService;
import com.marketcheck.marketchecker.domain.SteamAPIService;

import com.marketcheck.marketchecker.dto.ItemDTO;
import com.marketcheck.marketchecker.dto.SteamApiDTO;
import com.marketcheck.marketchecker.excel.ExcelProcessManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/search/steam")
public class SteamApiController
{
    private final SteamAPIService steamAPIService;
    private final DbService dbService;
    private final ExcelProcessManager excelProcessManager;
    private final String CASE_SHEET = "Case Prices";
    private final String SKIN_SHEET = "Skin Prices";

    public SteamApiController(SteamAPIService steamAPIService, DbService dbService, ExcelProcessManager excelProcessManager)
    {
        this.steamAPIService = steamAPIService;
        this.dbService = dbService;
        this.excelProcessManager = excelProcessManager;
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

    @PostMapping("/cases/excel")
    @ResponseBody
    public void createCasesExcel(@RequestBody Map<String, SteamApiDTO> items)
    {
        try {
            String currentDate = LocalDate.now().toString();
            System.out.println(items);

            for (Map.Entry<String, SteamApiDTO> entry : items.entrySet()) {
                String caseName = entry.getKey();
                SteamApiDTO caseData = entry.getValue();
                String lowestPrice = caseData.getLowestPrice();
                String medianPrice = caseData.getMedianPrice();
                String volume = caseData.getVolume();

                excelProcessManager.addCase(caseName, currentDate, volume, medianPrice, lowestPrice, CASE_SHEET);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/skins/excel")
    @ResponseBody
    public void createSkinsExcel(@RequestBody Map<String, SteamApiDTO> items)
    {
        try {
            String currentDate = LocalDate.now().toString();
            System.out.println(items);

            for (Map.Entry<String, SteamApiDTO> entry : items.entrySet()) {
                String skinName = entry.getKey();
                SteamApiDTO skinData = entry.getValue();
                String lowestPrice = skinData.getLowestPrice();
                String medianPrice = skinData.getMedianPrice();
                String volume = skinData.getVolume();

                excelProcessManager.addCase(skinName, currentDate, volume, medianPrice, lowestPrice, SKIN_SHEET);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
