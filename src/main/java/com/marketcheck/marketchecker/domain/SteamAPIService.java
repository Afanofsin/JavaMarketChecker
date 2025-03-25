package com.marketcheck.marketchecker.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketcheck.marketchecker.dto.ItemDTO;
import com.marketcheck.marketchecker.dto.SteamApiDTO;


import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SteamAPIService {

    private final RestTemplate restTemplate;
    //private final String URL = "https://steamcommunity.com/market/priceoverview/?country=NL&currency=3&appid=730&market_hash_name=";
    private final String URL = "https://steamcommunity.com/market/priceoverview/?currency=3&appid=730&market_hash_name=";

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
            SteamApiDTO skinPrice = fetchSkinsPrices(skin.getId(),skin.getName(), wearName);
            map.put(skin.getId()+ " | " + skin.getName()+ " | " + wearName, skinPrice);
        }
        return map;
    }

//    private SteamApiDTO fetchContainerPrices (String containerName)
//    {
//        if(containerName == null || containerName.isEmpty()) return null;
//        String targetURL;
//        if(containerName.equals("X-Ray P250 Package")) {
//            targetURL = URL + URLEncoder.encode(containerName, StandardCharsets.UTF_8);
//        }
//        else if (containerName.equals("Dreams & Nightmares Case"))
//        {
//            targetURL = "https://steamcommunity.com/market/priceoverview/?appid=730&market_hash_name=Dreams+&+Nightmares+Case";
//        }
//        else{
//            targetURL = URL + containerName.replace("&", "%26").replace(" ", "+");
//        }
//
//        System.out.println("targetURL: " + targetURL);
//
//        return restTemplate.getForObject(targetURL, SteamApiDTO.class);
//
//    }
    private SteamApiDTO fetchContainerPrices (String containerName)
    {
        String targetURL = URL + containerName;
        System.out.println("targetURL: " + targetURL);

        return queryApi(containerName);
    }

//    private SteamApiDTO fetchSkinsPrices (String weaponName, String skinName, String wearName)
//    {
//        //String targetURL = URL + URLEncoder.encode(weaponName, StandardCharsets.UTF_8) + "%20%7C%20" +
//        //                         URLEncoder.encode(skinName, StandardCharsets.UTF_8) + "%20%28" +
//        //                         URLEncoder.encode(wearName, StandardCharsets.UTF_8) + "%29";
//        String crafterURL = weaponName + " | " + skinName + " (" + wearName + ")";
//        String targetURL = URL + crafterURL.replace(" ", "%20").replace("|", "%7C").replace("(", "%28").replace(")", "%29");
//        System.out.println("targetURL: " + targetURL);
//        return restTemplate.getForObject(targetURL, SteamApiDTO.class);
//    }

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
