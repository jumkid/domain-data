package com.jumkid.domain.service;

import com.jumkid.domain.controller.dto.DomainData;

import java.io.InputStream;
import java.util.List;

public interface DomainDataService {

    DomainData getDomainData(Long id);

    List<DomainData> getDomainData(String industry, String name);

    DomainData saveDomainData(String industry, String name, String value);

    DomainData updateDomainData(Long id, DomainData partialDomainData);

    void deleteDomainData(Long id);

    List<DomainData> importDomainData(InputStream is);

}
