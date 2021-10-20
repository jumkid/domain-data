package com.jumkid.domain.service;

import com.jumkid.domain.controller.dto.DomainData;
import com.jumkid.domain.model.DomainDataEntity;
import com.jumkid.domain.repository.DomainDataRepository;
import com.jumkid.domain.service.mapper.DomainDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<DomainData> getDomainData(String industry, String name) {
        List<DomainDataEntity> entities = domainDataRepository.findByIndustryAndNameOrderByValue(industry, name);

        log.debug("Found {} domain data records", entities.size());

        return domainDataMapper.entitiesToDTOs(entities);
    }

    @Override
    public DomainData saveDomainData(DomainData domainData) {
        DomainDataEntity domainDataEntity = domainDataRepository.save(domainDataMapper.dtoToEntity(domainData));

        log.debug("Domain data saved with id {}", domainData.getId());

        return domainDataMapper.entityToDto(domainDataEntity);
    }

    @Override
    public void deleteDomainData(Long domainId) {
        try {
            domainDataRepository.deleteById(domainId);
        } catch (Exception e){
            log.error("failed to delete domain data by id {} due to {}", domainId, e.getMessage());
        }
    }

    @Override
    public Integer importDomainData(InputStream is) {
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
            return domainDataList.size();
        } catch (IOException e) {
            log.error("Unable to extra data from the import file {}", e.getMessage());
        }
        return 0;
    }

}
