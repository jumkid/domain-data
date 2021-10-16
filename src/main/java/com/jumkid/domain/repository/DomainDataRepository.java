package com.jumkid.domain.repository;

import com.jumkid.domain.model.DomainDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DomainDataRepository extends JpaRepository<DomainDataEntity, Long> {

    List<DomainDataEntity> findByIndustryAndNameOrderByValue(String industry, String name);

}
