package com.marketcheck.marketchecker.domain;

import java.io.IOException;
import java.util.List;

import com.marketcheck.marketchecker.dto.ItemDTO;
import org.springframework.stereotype.Service;

@Service
public class SkinService {

    private final SkinScraper scraper;

    public SkinService(SkinScraper scraper) {this.scraper = scraper;}

    public List<ItemDTO> getContainersByQuery(String itemType, String containerName, int limiter) throws IOException{
        return scraper.getCases(itemType, containerName, limiter);
    }

    public List<ItemDTO> getSkinsByQuery(String weaponName, String skinName, int limiter) throws IOException{
        return scraper.getSkins(weaponName,skinName, limiter);
    }
}

