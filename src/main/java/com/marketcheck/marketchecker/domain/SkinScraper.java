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
    public List<SkinItem> getSkins(String query, int limiter) throws IOException {
        String url = "https://csgoskins.gg/?query=" + query.replace(" ", "+");

        Document doc = Jsoup.connect(url).get();

        List<SkinItem> skins = new ArrayList<>();
        Elements items = doc.select("div.bg-gray-800.rounded-sm.shadow-md");
        int counter = 0;
        for (Element item : items) {
            if(counter >= limiter) break;
            Element weaponElement = item.selectFirst("span.block.text-gray-400.text-sm.truncate");
            String weapon = weaponElement != null ? weaponElement.text().trim() : "Unknown weapon";

            Element skinElement = item.selectFirst("span.block.text-lg.leading-7.truncate");
            String skin = skinElement != null ? skinElement.text().trim() : "Unknown skin";

            skins.add(new SkinItem(weapon, skin));
            counter++;
        }

        return skins;
    }

    @Data
    public class SkinItem{
        private final String weapon;
        private final String skin;
        //private final String imageURL;

        public SkinItem(String weapon, String skin){
            this.weapon = weapon;
            this.skin = skin;
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
