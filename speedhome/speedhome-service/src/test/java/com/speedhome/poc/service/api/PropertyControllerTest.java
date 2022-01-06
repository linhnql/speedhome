package com.speedhome.poc.service.api;

import com.speedhome.poc.api.model.Properties;
import com.speedhome.poc.api.model.Property;
import com.speedhome.poc.api.model.PropertyApproveReq;
import com.speedhome.poc.api.model.PropertyReq;
import com.speedhome.poc.service.mock.PropertyData;
import com.speedhome.poc.service.service.PropertyService;
import com.speedhome.poc.service.validator.PropertySearchValidator;
import com.speedhome.poc.service.validator.PropertyValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class PropertyControllerTest {
    private final String API_KEY = "MOCK_API_KEY";

    @InjectMocks
    PropertyController controller;

    @Mock
    PropertyService service;

    @Mock
    PropertyValidator validator;

    @Mock
    PropertySearchValidator searchValidator;

    @Test
    public void testEndpointCreateProperty() {
        when(service.createProperty(any(PropertyReq.class)))
                .thenReturn(PropertyData.mockProperty());

        ResponseEntity<Property> responseEntity =
                controller.addProperty(API_KEY, PropertyData.mockPropertyReq());

        assertStatus200(responseEntity.getStatusCode());
        assertProperty(responseEntity.getBody());
    }

    @Test
    public void testEndpointUpdateProperty() {
        when(service.updateProperty(anyString(), any(PropertyReq.class)))
                .thenReturn(PropertyData.mockProperty());

        ResponseEntity<Property> responseEntity =
                controller.updateProperty(API_KEY, PropertyData.PROPERTY_ID, PropertyData.mockPropertyReq());

        assertStatus200(responseEntity.getStatusCode());
        assertProperty(responseEntity.getBody());
    }

    @Test
    public void testEndpointApproveProperty() {
        when(service.approveProperty(anyString(), any(PropertyApproveReq.class)))
                .thenReturn(PropertyData.mockProperty());

        ResponseEntity<Property> responseEntity =
                controller.approveProperty(API_KEY,
                        PropertyData.PROPERTY_ID, PropertyData.mockPropertyApproveReq());

        assertStatus200(responseEntity.getStatusCode());
        assertProperty(responseEntity.getBody());
    }

    @Test
    public void testEndpointSearchProperty() {
        when(service.search(anyString()))
                .thenReturn(PropertyData.mockProperties());

        ResponseEntity<Properties> responseEntity =
                controller.search(API_KEY, "MOCK_TERM");

        assertStatus200(responseEntity.getStatusCode());
        assertProperties(responseEntity.getBody());
    }

    private void assertProperty(Property actual) {
        assertThat(actual.getId(), is(PropertyData.PROPERTY_ID));
        assertThat(actual.getName(), is(PropertyData.NAME));
        assertThat(actual.getDesc(), is(PropertyData.DESC));
        assertThat(actual.getPrice(), is(PropertyData.PRICE));
        assertThat(actual.getType().name(), is(Property.TypeEnum.AGRICULTURE.name()));
        assertThat(actual.getStatus().name(), is(Property.StatusEnum.PENDING.name()));
    }

    private void assertProperties(Properties actual) {
        assertProperty(actual.get(0));
    }

    private void assertStatus200(HttpStatus actual) {
        assertThat(actual, is(HttpStatus.OK));
    }
}
