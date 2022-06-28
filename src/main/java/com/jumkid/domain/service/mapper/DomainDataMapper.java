package com.jumkid.domain.service.mapper;

import com.jumkid.domain.controller.dto.DomainData;
import com.jumkid.domain.model.DomainDataEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel="spring")
public interface DomainDataMapper {

    @Mapping(source = "domainId", target="id")
    DomainData entityToDto(DomainDataEntity entity);

    @Mapping(target="domainId", source = "dto.id")
    DomainDataEntity dtoToEntity(DomainData dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(DomainData partialDto, @MappingTarget DomainDataEntity updateEntity);

    List<DomainData> entitiesToDTOs(List<DomainDataEntity> entities);

}
