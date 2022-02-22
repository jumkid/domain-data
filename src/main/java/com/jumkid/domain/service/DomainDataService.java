package com.jumkid.domain.service;

import com.jumkid.domain.controller.dto.DomainData;

import java.io.InputStream;
import java.util.List;

public interface DomainDataService {

    List<DomainData> getDomainData(String industry, String name);

    DomainData saveDomainData(DomainData domainData);

    void deleteDomainData(Long domainId);

    List<DomainData> importDomainData(InputStream is);

}
