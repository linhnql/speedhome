package com.speedhome.poc.service.mock;

import com.speedhome.poc.api.model.Properties;
import com.speedhome.poc.api.model.Property;
import com.speedhome.poc.api.model.PropertyApproveReq;
import com.speedhome.poc.api.model.PropertyReq;
import com.speedhome.poc.service.entity.PropertyEntity;

import java.util.Arrays;
import java.util.List;

public class PropertyData {
    public static final String PROPERTY_ID = "c73095df-a31d-45a0-8d57-8c43108830c7";
    public static final String NAME = "Old Property";
    public static final String DESC = "This is a beautiful property";
    public static final double PRICE = 1500.0;
    public static final String STATUS_COMMENT = "Just created";
    public static final String UPDATE_USER_ID = "khoavu";

    public static Property mockProperty() {
        Property property = new Property();

        property.setId(PROPERTY_ID);
        property.setName(NAME);
        property.setDesc(DESC);
        property.setPrice(PRICE);
        property.setType(Property.TypeEnum.AGRICULTURE);
        property.setStatus(Property.StatusEnum.PENDING);
        property.setStatusComment(STATUS_COMMENT);

        return property;
    }

    public static Properties mockProperties() {
        Properties properties = new Properties();

        properties.add(mockProperty());

        return properties;
    }

    public static PropertyReq mockPropertyReq() {
        PropertyReq propertyReq = new PropertyReq();

        propertyReq.setName(NAME);
        propertyReq.setDesc(DESC);
        propertyReq.setPrice(PRICE);
        propertyReq.setType(PropertyReq.TypeEnum.AGRICULTURE);
        propertyReq.setStatus(PropertyReq.StatusEnum.PENDING);
        propertyReq.setUserId(UPDATE_USER_ID);

        return propertyReq;
    }

    public static PropertyApproveReq mockPropertyApproveReq() {
        PropertyApproveReq approveReq = new PropertyApproveReq();

        approveReq.setStatus(PropertyApproveReq.StatusEnum.APPROVED);
        approveReq.setStatusComment(STATUS_COMMENT);
        approveReq.setUserId(UPDATE_USER_ID);

        return approveReq;
    }

    public static PropertyEntity mockPropertyEntity() {
        PropertyEntity entity = new PropertyEntity();

        entity.setId(PROPERTY_ID);
        entity.setName(NAME);
        entity.setDesc(DESC);
        entity.setPrice(PRICE);
        entity.setType(PropertyReq.TypeEnum.AGRICULTURE.name());
        entity.setStatus(Property.StatusEnum.PENDING.name());
        entity.setStatusComment(STATUS_COMMENT);

        return entity;
    }

    public static List<PropertyEntity> mockPropertyEntities() {
        return Arrays.asList(mockPropertyEntity());
    }
}
