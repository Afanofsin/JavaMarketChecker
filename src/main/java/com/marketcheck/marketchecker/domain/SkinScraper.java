package com.marketcheck.marketchecker.domain;

import lombok.Data;
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

    public List<SkinItem> getCases(String findType, String containerName, int limiter) throws IOException {
        String url = "https://csgoskins.gg/categories/weapon-case";
        //String url = "https://csgoskins.gg/?query=" + containerName.replace(" ", "+");
        System.out.println(url);

        Document doc = Jsoup.connect(url)
                .get();

        return switch (findType) {
            case "All" -> {
                System.out.println("All selected");
                yield findCases(doc, 50, null);
            }
            case "One" -> {
                System.out.println("One selected");
                yield findCases(doc, 50, containerName);
            }
            case "Several" -> {
                System.out.println("Several selected");
                yield findCases(doc, limiter, null);
            }
            default -> null;
        };
    }

    private List<SkinItem> findCases(Document doc, int limiter, String containerName){
        List<SkinItem> skins = new ArrayList<>();
        Elements items = doc.select("div.bg-gray-800.rounded-sm.shadow-md");// "div.bg-gray-800.rounded-sm.shadow-md"

        int counter = 0;

        if (containerName != null)
        {
            for (Element item : items) {
                if(counter >= limiter && limiter <= 49) break;

                Element containerElement = item.selectFirst("span.block.text-lg.leading-6.truncate.mt-3");//"span.block.text-gray-400.text-sm.truncate"
                if(containerElement == null) continue;
                System.out.println("Item " + counter + ": " + containerElement.text().trim());
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
                if(counter >= limiter && limiter <= 49) break;

                Element containerElement = item.selectFirst("span.block.text-lg.leading-6.truncate.mt-3"); //"span.block.text-gray-400.text-sm.truncate"
                if(containerElement == null) break;
                skins.add(processItem(containerElement, counter));
                counter++;
            }


        }
        return skins;
    }

    private SkinItem processItem(Element item, int counter)
    {
        String container = item != null ? item.text().trim() : "Unknown case";
        System.out.println("Item " + counter + ": " + container);
        return new SkinItem(String.valueOf(counter), container);
    }

    public List<SkinItem> getSkins(String weaponName, String skinName, int limiter) throws IOException {
        String url = "https://csgoskins.gg/?query=" + weaponName.replace(" ", "+")+ "+" + skinName.replace(" ", "+");
        //System.out.println(url);

        //OLD String url = "https://csgoskins.gg/?query=" + query.replace(" ", "+");

        Document doc = Jsoup.connect(url)
                .get();

        List<SkinItem> skins = new ArrayList<>();
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

            skins.add(new SkinItem(weapon, skin));
            counter++;
        }

        return skins;
    }

    @Data
    public static class SkinItem{
        private final String weapon;
        private final String skin;
        //private final String imageURL;

        public SkinItem(String weapon, String skin){
            this.weapon = weapon;
            this.skin = skin;
            //this.imageURL = imageUrl;
        }

        @Override
        public String toString() {
            return "SkinItem{" +
                    "weapon='" + weapon + '\'' +
                    ", skin='" + skin + '\'' +
                    '}';
        }
    }

}
