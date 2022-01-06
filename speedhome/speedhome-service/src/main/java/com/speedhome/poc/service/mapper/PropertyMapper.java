package com.speedhome.poc.service.mapper;

import com.speedhome.poc.api.model.Properties;
import com.speedhome.poc.api.model.Property;
import com.speedhome.poc.api.model.PropertyApproveReq;
import com.speedhome.poc.api.model.PropertyReq;
import com.speedhome.poc.service.entity.PropertyEntity;
import com.speedhome.poc.service.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.threeten.bp.Instant;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PropertyMapper {
    private final PropertyRepository repository;

    @Autowired
    public PropertyMapper(PropertyRepository repository) {
        this.repository = repository;
    }

    public PropertyEntity mapPropertyEntityFromPropertyReq(PropertyReq from) {
        PropertyEntity to = new PropertyEntity();

        to.setId(UUID.randomUUID().toString());
        to.setName(from.getName());
        to.setDesc(from.getDesc());
        to.setPrice(from.getPrice());
        to.setType(from.getType().name());
        to.setStatus(PropertyReq.StatusEnum.PENDING.name());
        to.setStatusComment("Just created");
        to.setStatusDate(new Timestamp(Instant.now().toEpochMilli()));
        to.setUpdatedDate(new Timestamp(Instant.now().toEpochMilli()));
        to.setUpdatedUserId(from.getUserId());

        return to;
    }

    public PropertyEntity mapPropertyEntityFromPropertyReq(String id, PropertyReq from) {
        PropertyEntity to = repository.getOne(id);

        to.setName(from.getName());
        to.setDesc(from.getDesc());
        to.setPrice(from.getPrice());
        to.setType(from.getType().name());
        to.setUpdatedDate(new Timestamp(Instant.now().toEpochMilli()));
        to.setUpdatedUserId(from.getUserId());

        return to;
    }

    public PropertyEntity mapPropertyEntityFromPropertyApproveReq(String id, PropertyApproveReq from) {
        PropertyEntity to = repository.getOne(id);

        to.setStatus(from.getStatus().name());
        to.setStatusComment(from.getStatusComment());
        to.setStatusDate(new Timestamp(Instant.now().toEpochMilli()));
        to.setUpdatedDate(new Timestamp(Instant.now().toEpochMilli()));
        to.setUpdatedUserId(from.getUserId());

        return to;
    }

    public Property mapPropertyFromPropertyEntity(PropertyEntity from) {
        Property to = new Property();

        to.setId(from.getId());
        to.setName(from.getName());
        to.setDesc(from.getDesc());
        to.setPrice(from.getPrice());
        to.setType(Property.TypeEnum.fromValue(from.getType()));
        to.setStatus(Property.StatusEnum.fromValue(from.getStatus()));
        to.setStatusComment(from.getStatusComment());

        return to;
    }

    public Properties mapPropertiesFromPropertyEntities(List<PropertyEntity> from) {
        return from.stream()
                .map(this::mapPropertyFromPropertyEntity)
                .collect(Collectors.toCollection(Properties::new));
    }
}
