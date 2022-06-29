package com.jumkid.domain.service;

import com.jumkid.domain.controller.dto.DomainData;
import com.jumkid.domain.exception.DomainDataNotFoundException;
import com.jumkid.domain.model.DomainDataEntity;
import com.jumkid.domain.repository.DomainDataRepository;
import com.jumkid.domain.service.mapper.DomainDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DomainDataServiceImpl implements DomainDataService{

    private final DomainDataRepository domainDataRepository;

    private final DomainDataMapper domainDataMapper = Mappers.getMapper(DomainDataMapper.class);

    private static final String COMMA_DELIMITER = ",";

    @Autowired
    public DomainDataServiceImpl(DomainDataRepository domainDataRepository) {
        this.domainDataRepository = domainDataRepository;
    }

    @Override
    @Cacheable(key = "#id", value = "domain-data")
    public DomainData getDomainData(Long id) {
        Optional<DomainDataEntity> optional = domainDataRepository.findById(id);
        if (optional.isPresent()) {
            return domainDataMapper.entityToDto(optional.get());
        } else {
            throw new DomainDataNotFoundException(id);
        }
    }

    @Override
    @Cacheable(key = "#industry+#name", value = "domain-data")
    public List<DomainData> getDomainData(String industry, String name) {
        List<DomainDataEntity> entities = domainDataRepository.findByIndustryAndNameOrderByValue(industry, name);

        log.debug("Found {} domain data records", entities.size());

        return domainDataMapper.entitiesToDTOs(entities);
    }

    @Override
    @CacheEvict(value = "domain-data", allEntries = true)
    public DomainData saveDomainData(String industry, String name, String value) {
        DomainDataEntity entity = DomainDataEntity.builder()
                .industry(industry)
                .name(name)
                .value(value)
                .build();

        entity = domainDataRepository.save(entity);
        log.info("save new domain data with id: {}", entity.getDomainId());

        return domainDataMapper.entityToDto(entity);
    }

    @Override
    @Transactional
    @CacheEvict(value = "domain-data", allEntries = true)
    public DomainData updateDomainData(Long id, DomainData partialDomainData) {
        Optional<DomainDataEntity> optional = domainDataRepository.findById(id);
        if (optional.isPresent()) {
            DomainDataEntity updateEntity = optional.get();
            domainDataMapper.updateEntityFromDto(partialDomainData, updateEntity);

            updateEntity = domainDataRepository.save(updateEntity);
            log.debug("Updated domain data with id {}", id);

            return domainDataMapper.entityToDto(updateEntity);
        } else {
            throw new DomainDataNotFoundException(id);
        }
    }

    @Override
    @CacheEvict(value = "domain-data", allEntries = true)
    public Boolean deleteDomainData(Long id) {
        try {
            domainDataRepository.deleteById(id);
            return true;
        } catch (Exception e){
            log.error("failed to delete domain data by id {} due to {}", id, e.getMessage());
            return false;
        }
    }

    @Override
    @CacheEvict(value = "domain-data", allEntries = true)
    @Transactional
    public List<DomainData> importDomainData(InputStream is) {
        log.info("importing domain data");
        List<DomainDataEntity> domainDataList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            while (reader.ready()) {
                String line = reader.readLine();
                String[] domainStr = line.split(COMMA_DELIMITER);
                domainDataList.add(DomainDataEntity.builder()
                        .industry(domainStr[0].trim())
                        .name(domainStr[1].trim())
                        .value(domainStr[2].trim())
                .build());
            }

            if (!domainDataList.isEmpty()) {
                domainDataList = domainDataRepository.saveAll(domainDataList);
            }
            log.info("{} records are imported", domainDataList.size());
            return domainDataMapper.entitiesToDTOs(domainDataList);
        } catch (IOException e) {
            log.error("Unable to extra data from the import file {}", e.getMessage());
        }
        return Collections.emptyList();
    }

}
