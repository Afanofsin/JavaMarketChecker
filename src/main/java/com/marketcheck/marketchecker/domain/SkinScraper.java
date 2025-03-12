package com.marketcheck.marketchecker.domain;

import com.marketcheck.marketchecker.dto.ItemDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SkinScraper {
    // For Containers
    private final int MAX_LIMIT = 50;

    public List<ItemDTO> getCases(String findType, String containerName, int limiter) throws IOException {
        String url = "https://csgoskins.gg/categories/weapon-case";
        //String url = "https://csgoskins.gg/?query=" + containerName.replace(" ", "+");
        System.out.println(url);

        Document doc = Jsoup.connect(url)
                .get();

        return switch (findType) {
            case "All" -> {
                System.out.println("All selected");
                yield findCases(doc, MAX_LIMIT, null);
            }
            case "One" -> {
                System.out.println("One selected");
                yield findCases(doc, MAX_LIMIT, containerName);
            }
            case "Several" -> {
                System.out.println("Several selected");
                yield findCases(doc, limiter, null);
            }
            default -> null;
        };
    }

    private List<ItemDTO> findCases(Document doc, int limiter, String containerName){
        List<ItemDTO> skins = new ArrayList<>();
        Elements items = doc.select("div.bg-gray-800.rounded-sm.shadow-md");// "div.bg-gray-800.rounded-sm.shadow-md"

        int counter = 0;

        if (containerName != null)
        {
            for (Element item : items) {
                if(counter >= limiter && limiter <= MAX_LIMIT-1) break;

                Element containerElement = item.selectFirst("span.block.text-lg.leading-6.truncate.mt-3");//"span.block.text-gray-400.text-sm.truncate"
                if(containerElement == null) continue;

                //System.out.println("Item " + counter + ": " + containerElement.text().trim());

                if(containerElement.text().trim().equalsIgnoreCase(containerName))
                {
                    skins.add(processItem(containerElement, counter));
                }

                counter++;
            }
        }
        else
        {
            for (Element item : items) {
                if(counter >= limiter && limiter <= MAX_LIMIT-1) break;

                Element containerElement = item.selectFirst("span.block.text-lg.leading-6.truncate.mt-3"); //"span.block.text-gray-400.text-sm.truncate"
                if(containerElement == null) break;
                skins.add(processItem(containerElement, counter));
                counter++;
            }


        }
        return skins;
    }

    private ItemDTO processItem(Element item, int counter)
    {
        String container = item != null ? item.text().trim() : "Unknown";
        System.out.println("Item " + counter + ": " + container);
        ItemDTO dto = new ItemDTO();
        dto.setId(String.valueOf(counter));
        dto.setName(container);

        return dto;
    }

    public List<ItemDTO> getSkins(String weaponName, String skinName, int limiter) throws IOException {
        String url = "https://csgoskins.gg/?query=" + weaponName.replace(" ", "+")+ "+" + skinName.replace(" ", "+");
        //System.out.println(url);

        Document doc = Jsoup.connect(url)
                .get();

        List<ItemDTO> skins = new ArrayList<>();
        Elements items = doc.select("div.bg-gray-800.rounded-sm.shadow-md");

        //System.out.println("Document HTML: " + doc.html());// "div.bg-gray-800.rounded-sm.shadow-md"
        int counter = 0;
        for (Element item : items) {
            if(counter >= limiter) break;

            Element weaponElement = item.selectFirst("span.block.text-gray-400.text-sm.truncate"); //"span.block.text-gray-400.text-sm.truncate"
            String weapon = weaponElement != null ? weaponElement.text().trim() : "Unknown weapon";

            Element skinElement = item.selectFirst("span.block.text-lg.leading-7.truncate"); //"span.block.text-lg.leading-7.truncate"
            String skin = skinElement != null ? skinElement.text().trim() : "Unknown skin";
            //System.out.println(weapon + " | " + skin);

            ItemDTO dto = new ItemDTO();
            dto.setId(weapon);
            dto.setName(skin);

            skins.add(dto);
            counter++;
        }

        return skins;
    }

}
