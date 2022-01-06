package com.speedhome.poc.service.mapper;

import com.speedhome.poc.api.model.Properties;
import com.speedhome.poc.api.model.Property;
import com.speedhome.poc.api.model.PropertyApproveReq;
import com.speedhome.poc.api.model.PropertyReq;
import com.speedhome.poc.service.entity.PropertyEntity;
import com.speedhome.poc.service.mock.PropertyData;
import com.speedhome.poc.service.repository.PropertyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertyMapperTest {

    @InjectMocks
    PropertyMapper mapper;

    @Mock
    PropertyRepository repository;

    @Test
    public void ensureMapPropertyEntityFromPropertyReqForAdd() {

        PropertyReq propertyReq = PropertyData.mockPropertyReq();

        PropertyEntity entity = mapper.mapPropertyEntityFromPropertyReq(propertyReq);

        assertThat(entity.getId() != null, is(true));
        assertThat(entity.getName(), is(propertyReq.getName()));
        assertThat(entity.getDesc(), is(propertyReq.getDesc()));
        assertThat(entity.getPrice(), is(propertyReq.getPrice()));
        assertThat(entity.getStatus(), is(PropertyReq.StatusEnum.PENDING.name()));
        assertThat(entity.getStatusComment(), is(PropertyData.STATUS_COMMENT));
        assertThat(entity.getType(), is(propertyReq.getType().name()));
        assertThat(entity.getUpdatedUserId(), is(propertyReq.getUserId()));
        assertThat(entity.getUpdatedDate() != null, is(true));
        assertThat(entity.getStatusDate() != null, is(true));
    }

    @Test
    public void ensureMapPropertyEntityFromPropertyReqForUpdate() {
        PropertyEntity propertyEntity = PropertyData.mockPropertyEntity();

        when(repository.getOne(PropertyData.PROPERTY_ID)).thenReturn(propertyEntity);

        PropertyReq propertyReq = PropertyData.mockPropertyReq();

        PropertyEntity entity = mapper.mapPropertyEntityFromPropertyReq(PropertyData.PROPERTY_ID, propertyReq);

        assertThat(entity.getName(), is(propertyReq.getName()));
        assertThat(entity.getDesc(), is(propertyReq.getDesc()));
        assertThat(entity.getPrice(), is(propertyReq.getPrice()));
        assertThat(entity.getType(), is(propertyReq.getType().name()));
        assertThat(entity.getUpdatedUserId(), is(propertyReq.getUserId()));

        assertThat(entity.getId() , is(propertyEntity.getId()));
        assertThat(entity.getStatusDate() , is(propertyEntity.getStatusDate()));
        assertThat(entity.getStatus(), is(propertyEntity.getStatus()));
        assertThat(entity.getStatusComment(), is(propertyEntity.getStatusComment()));

    }

    @Test
    public void ensureMapPropertyEntityFromPropertyApproveReq() {
        PropertyEntity propertyEntity = PropertyData.mockPropertyEntity();

        when(repository.getOne(PropertyData.PROPERTY_ID)).thenReturn(propertyEntity);

        PropertyApproveReq approveReq = PropertyData.mockPropertyApproveReq();

        PropertyEntity entity = mapper.mapPropertyEntityFromPropertyApproveReq(PropertyData.PROPERTY_ID, approveReq);

        assertThat(entity.getId() , is(propertyEntity.getId()));

        assertThat(entity.getStatus(), is(propertyEntity.getStatus()));
        assertThat(entity.getStatusComment(), is(propertyEntity.getStatusComment()));

    }

    @Test
    public void ensureMapPropertyFromPropertyEntity() {
        PropertyEntity propertyEntity = PropertyData.mockPropertyEntity();

        Property property = mapper.mapPropertyFromPropertyEntity(propertyEntity);

        assertProperty(property, propertyEntity);
    }

    @Test
    public void ensureMapPropertiesFromPropertyEntities() {
        List<PropertyEntity> propertyEntities = PropertyData.mockPropertyEntities();

        Properties properties = mapper.mapPropertiesFromPropertyEntities(propertyEntities);

        assertProperties(properties, propertyEntities);
    }

    private void assertProperty(Property actual, PropertyEntity input) {
        assertThat(actual.getId(), is(input.getId()));
        assertThat(actual.getName(), is(input.getName()));
        assertThat(actual.getDesc(), is(input.getDesc()));
        assertThat(actual.getPrice(), is(input.getPrice()));
        assertThat(actual.getType().name(), is(input.getType()));
        assertThat(actual.getStatus().name(), is(input.getStatus()));
        assertThat(actual.getStatusComment(), is(input.getStatusComment()));
    }

    private void assertProperties(Properties actual, List<PropertyEntity> input) {
        assertProperty(actual.get(0), input.get(0));
    }

}
