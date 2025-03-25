package com.marketcheck.marketchecker.db;

import com.marketcheck.marketchecker.entities.SkinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinsRepository extends JpaRepository<SkinEntity, Long> { }
