package com.megamaker.catalogservice.controller;

import com.megamaker.catalogservice.entity.CatalogEntity;
import com.megamaker.catalogservice.mapper.CatalogMapper;
import com.megamaker.catalogservice.service.CatalogService;
import com.megamaker.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {
    private final Environment env;
    private final CatalogService catalogService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in Catalog Service on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
        Iterable<CatalogEntity> userList = catalogService.getAllCatalogs();
        List<ResponseCatalog> result = new ArrayList<>();
        userList.forEach(userEntity ->
                result.add(CatalogMapper.INSTANCE.toResponseCatalog(userEntity))
        );

        return ResponseEntity.ok(result);
    }
}
