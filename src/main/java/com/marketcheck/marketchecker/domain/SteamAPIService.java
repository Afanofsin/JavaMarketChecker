package com.marketcheck.marketchecker.domain;

import com.marketcheck.marketchecker.dto.ItemDTO;
import com.marketcheck.marketchecker.dto.SteamApiDTO;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SteamAPIService {

    private final RestTemplate restTemplate;
    private final String URL = "https://steamcommunity.com/market/priceoverview?country=NL&currency=3&appid=730&market_hash_name=";

    public SteamAPIService(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    public Map<String, SteamApiDTO> getContainerPrices(List<ItemDTO> containers)
    {
        if(containers == null) return null;

        Map<String, SteamApiDTO> map = new HashMap<>();
        for(ItemDTO container : containers)
        {
            SteamApiDTO containerPrice = fetchContainerPrices(container.getName());
            map.put(container.getName(), containerPrice);
        }
        return map;
    }

    public Map<String, SteamApiDTO> getSkinsPrices(List<ItemDTO> skins, String wearName)
    {
        if(skins == null || wearName == null) return null;

        Map<String, SteamApiDTO> map = new HashMap<>();
        for(ItemDTO skin : skins)
        {
            SteamApiDTO skinPrice = fetchSkinsPrices(skin.getName(), wearName);
            map.put(skin.getId()+ " | " + skin.getName()+ " | " + wearName, skinPrice);
        }
        return map;
    }

    private SteamApiDTO fetchContainerPrices (String containerName)
    {
        String targetURL = URL + containerName.replace("&", "%26").replace(" ", "%20");
        System.out.println("targetURL: " + targetURL);
        return restTemplate.getForObject(targetURL, SteamApiDTO.class);

    }

    private SteamApiDTO fetchSkinsPrices (String skinNames, String wearName)
    {
        return restTemplate.getForObject(URL + skinNames + wearName, SteamApiDTO.class);
    }
}
