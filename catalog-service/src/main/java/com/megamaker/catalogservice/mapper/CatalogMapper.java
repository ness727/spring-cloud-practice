package com.megamaker.catalogservice.mapper;

import com.megamaker.catalogservice.entity.CatalogEntity;
import com.megamaker.catalogservice.vo.ResponseCatalog;
import org.apache.coyote.Response;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CatalogMapper {
    CatalogMapper INSTANCE = Mappers.getMapper(CatalogMapper.class);

    ResponseCatalog toResponseCatalog(CatalogEntity catalogEntity);
}
