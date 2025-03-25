package com.marketcheck.marketchecker.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketcheck.marketchecker.dto.ItemDTO;
import com.marketcheck.marketchecker.dto.SteamApiDTO;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SteamAPIService {


    private final String URL = "https://steamcommunity.com/market/priceoverview/?currency=3&appid=730&market_hash_name=";

    public SteamAPIService()
    {}

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
            SteamApiDTO skinPrice = fetchSkinsPrices(skin.getId(),skin.getName(), wearName);
            map.put(skin.getId()+ " | " + skin.getName()+ " | " + wearName, skinPrice);
        }
        return map;
    }

    private SteamApiDTO fetchContainerPrices (String containerName)
    {
        String targetURL = URL + containerName;
        System.out.println("targetURL: " + targetURL);

        return queryApi(containerName);
    }

    private SteamApiDTO fetchSkinsPrices (String weaponName, String skinName, String wearName)
    {
        String crafterURL = weaponName + " | " + skinName + " (" + wearName + ")";

        String targetURL = URL + crafterURL;
        System.out.println("targetURL: " + targetURL);

        return queryApi(crafterURL);
    }

    private SteamApiDTO queryApi(String itemName)
    {
        try {
            HttpResponse<JsonNode> priceOverview = Unirest.get(URL)
                    .queryString("appid", 730)
                    .queryString("market_hash_name", itemName)
                    .queryString("currency", 3)
                    .asJson();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(priceOverview.getBody().toString(), SteamApiDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
