package com.marketcheck.marketchecker.db;

import com.marketcheck.marketchecker.dto.SteamApiDTO;
import com.marketcheck.marketchecker.entities.CaseEntity;
import com.marketcheck.marketchecker.entities.SkinEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class DbService
{
    private final CaseRepository caseRepository;
    private final SkinsRepository skinsRepository;

    public DbService(CaseRepository caseRepository, SkinsRepository skinsRepository)
    {
        this.caseRepository = caseRepository;
        this.skinsRepository = skinsRepository;
    }

    public List<CaseEntity> getAllCases()
    {
        return this.caseRepository.findAll();
    }

    public List<SkinEntity> getAllSkins()
    {
        return this.skinsRepository.findAll();
    }

    public void saveCases(Map<String, SteamApiDTO> prices)
    {
        List<CaseEntity> cases = convertDTOEntity(prices, CaseEntity.class);
        caseRepository.saveAll(cases);
    }

    public void saveSkins(Map<String, SteamApiDTO> prices)
    {
        List<SkinEntity> skins = convertDTOEntity(prices, SkinEntity.class);
        skinsRepository.saveAll(skins);
    }

    public <T> List<T> convertDTOEntity(Map<String, SteamApiDTO> prices, Class<T> entityType)
    {
        return prices.entrySet().stream()
                .map(entry -> createEntity(entityType, entry.getKey(), entry.getValue()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private <T> T createEntity(Class<T> entityType, String item, SteamApiDTO price)
    {
        try
        {
            if(entityType.equals(CaseEntity.class))
            {
                LocalDateTime now = LocalDateTime.now();
                now = now.withNano(0);
                return entityType.cast(new CaseEntity(
                        null,
                        item,
                        now,
                        price.getLowestPrice() != null ? price.getLowestPrice() : "0",
                        price.getVolume() != null ? price.getLowestPrice() : "0",
                        price.getMedianPrice() != null ? price.getLowestPrice() : "0"
                ));
            } else if (entityType.equals(SkinEntity.class))
            {
                LocalDateTime now = LocalDateTime.now();
                return entityType.cast(new SkinEntity(
                        null,
                        item,
                        now,
                        price.getLowestPrice() != null ? price.getLowestPrice() : "0",
                        price.getVolume() != null ? price.getLowestPrice() : "0",
                        price.getMedianPrice() != null ? price.getLowestPrice() : "0"
                ));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
