package com.jumkid.domain.service.mapper;

import com.jumkid.domain.controller.dto.DomainData;
import com.jumkid.domain.model.DomainDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel="spring")
public interface DomainDataMapper {

    @Mappings({
            @Mapping(target="id", source = "entity.domainId")
    })
    DomainData entityToDTO(DomainDataEntity entity);

    @Mappings({
            @Mapping(target="domainId", source = "dto.id")
    })
    DomainDataEntity dtoToEntity(DomainData dto);

    List<DomainData> entitiesToDTOs(List<DomainDataEntity> entities);

}
