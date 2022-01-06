package com.speedhome.poc.service.service;

import com.speedhome.poc.api.model.Properties;
import com.speedhome.poc.api.model.Property;
import com.speedhome.poc.api.model.PropertyApproveReq;
import com.speedhome.poc.api.model.PropertyReq;
import com.speedhome.poc.service.entity.PropertyEntity;
import com.speedhome.poc.service.mapper.PropertyMapper;
import com.speedhome.poc.service.repository.PropertyRepository;
import com.speedhome.poc.service.search.PropertySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {
    private final PropertyRepository repository;

    private final PropertyMapper mapper;

    private final PropertySearch propertySearch;

    @Autowired
    public PropertyService(PropertyRepository repository,
                           PropertyMapper mapper,
                           PropertySearch propertySearch) {
        this.repository = repository;
        this.mapper = mapper;
        this.propertySearch = propertySearch;
    }

    public Property createProperty(PropertyReq req) {
        PropertyEntity property = mapper.mapPropertyEntityFromPropertyReq(req);

        return mapper.mapPropertyFromPropertyEntity(repository.save(property));
    }

    public Property updateProperty(String id, PropertyReq req) {
        PropertyEntity property = mapper.mapPropertyEntityFromPropertyReq(id, req);

        return mapper.mapPropertyFromPropertyEntity(repository.save(property));
    }

    public Property approveProperty(String id, PropertyApproveReq req) {
        PropertyEntity property = mapper.mapPropertyEntityFromPropertyApproveReq(id, req);

        return mapper.mapPropertyFromPropertyEntity(repository.save(property));
    }

    public Properties search(String term) {
        return mapper.mapPropertiesFromPropertyEntities(propertySearch.searchProperty(term));
    }
}
