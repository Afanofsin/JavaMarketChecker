package com.marketcheck.marketchecker.controller;

import com.marketcheck.marketchecker.db.DbService;
import com.marketcheck.marketchecker.domain.SkinService;
import com.marketcheck.marketchecker.dto.ItemDTO;
import com.marketcheck.marketchecker.entities.CaseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SkinController {

    private final SkinService skinService;
    private final DbService dbService;

    public SkinController(SkinService skinService, DbService dbService)
    {
        this.skinService = skinService;
        this.dbService = dbService;
    }

    // Endpoint to search for skins
    @GetMapping(params = {"weaponName", "skinName", "limiter"})
    public List<ItemDTO> searchSkins(@RequestParam String weaponName, @RequestParam String skinName, @RequestParam int limiter) {
        try {
            return skinService.getSkinsByQuery(weaponName,skinName,limiter);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return List.of(new ItemDTO());
        }
    }
    @GetMapping(params = {"findType", "containerName", "limiter"})
    public List<ItemDTO> searchContainers(@RequestParam String findType,@RequestParam String containerName,@RequestParam int limiter) {
        try {
            return skinService.getContainersByQuery(findType,containerName, limiter);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return List.of(new ItemDTO());
        }
    }

    @GetMapping("/allcases")
    public List<CaseEntity> getAllCases()
    {
        try {
            return dbService.getAllCases();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return List.of(new CaseEntity());
        }
    }


}
