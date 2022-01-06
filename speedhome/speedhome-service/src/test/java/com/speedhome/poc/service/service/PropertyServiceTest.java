package com.speedhome.poc.service.service;

import com.speedhome.poc.service.mapper.PropertyMapper;
import com.speedhome.poc.service.mock.PropertyData;
import com.speedhome.poc.service.repository.PropertyRepository;
import com.speedhome.poc.service.search.PropertySearch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PropertyServiceTest {
    @InjectMocks
    PropertyService service;

    @Mock
    PropertyRepository repository;

    @Mock
    PropertyMapper mapper;

    @Mock
    PropertySearch propertySearch;

    @Test
    public void createProperty() {
        service.createProperty(PropertyData.mockPropertyReq());
    }

    @Test
    public void updateProperty() {
        service.updateProperty(PropertyData.PROPERTY_ID, PropertyData.mockPropertyReq());
    }

    @Test
    public void approveProperty() {
        service.approveProperty(PropertyData.PROPERTY_ID, PropertyData.mockPropertyApproveReq());
    }

    @Test
    public void searchProperty() {
        service.search("MOCK_TERM");
    }
}
