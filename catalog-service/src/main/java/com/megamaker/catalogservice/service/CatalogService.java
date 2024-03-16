package com.megamaker.catalogservice.service;

import com.megamaker.catalogservice.entity.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
