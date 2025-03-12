package com.marketcheck.marketchecker.controller;


import com.marketcheck.marketchecker.domain.SkinService;
import com.marketcheck.marketchecker.dto.ItemDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(("/search"))
public class VisualSkinController
{
    private final SkinService skinService;

    // Inject SkinService via the constructor
    public VisualSkinController(SkinService skinService) {
        this.skinService = skinService;
    }

    @GetMapping("/cases")
    public String searchContainersVisual(
            @RequestParam(required = false, defaultValue = "All") String findType,
            @RequestParam(required = false, defaultValue = "") String containerName,
            @RequestParam(defaultValue = "10") int limiter,
            Model model)
    {
        try
        {
            List<ItemDTO> cases = skinService.getContainersByQuery(findType,containerName, limiter);
            System.out.println("Fetched Cases: " + cases);
            model.addAttribute("cases", cases);
        }
        catch (IOException e)
        {
            model.addAttribute("error", "Fail to fetch: " + e.getMessage());
            System.out.println("Error fetching cases: " + e.getMessage());
        }
        return "cases";
    }
}
