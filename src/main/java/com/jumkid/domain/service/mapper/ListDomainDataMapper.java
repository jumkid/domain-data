package com.jumkid.domain.service.mapper;

import com.jumkid.domain.controller.dto.DomainData;
import com.jumkid.domain.model.DomainDataEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring", uses = {DomainDataMapper.class})
public interface ListDomainDataMapper {

    List<DomainData> entitiesToDTOs(List<DomainDataEntity> entities);

}
