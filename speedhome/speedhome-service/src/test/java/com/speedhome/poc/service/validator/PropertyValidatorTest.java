package com.speedhome.poc.service.validator;

import com.speedhome.poc.api.model.PropertyReq;
import com.speedhome.poc.service.exception.BadRequestException;
import com.speedhome.poc.service.exception.EntityNotFoundException;
import com.speedhome.poc.service.mock.PropertyData;
import com.speedhome.poc.service.repository.PropertyRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertyValidatorTest {
    @InjectMocks
    PropertyValidator validator;

    @Mock
    PropertyRepository repository;

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Test
    public void validateUpdateProperty() {
        when(repository.existsById(PropertyData.PROPERTY_ID)).thenReturn(true);

        validator.validateUpdateProperty(PropertyData.PROPERTY_ID, PropertyData.mockPropertyReq());
    }

    @Test
    public void validateUpdatePropertyShowExceptionWhenWrongId() {
        expected.expect(EntityNotFoundException.class);
        expected.expectMessage("Property does not exist");

        when(repository.existsById(PropertyData.PROPERTY_ID)).thenReturn(false);

        validator.validateUpdateProperty(PropertyData.PROPERTY_ID, PropertyData.mockPropertyReq());
    }

    @Test
    public void validateAddProperty() {
        validator.validateAddProperty(PropertyData.mockPropertyReq());
    }

    @Test
    public void validateAddPropertyShowExceptionWhenRequiredFieldIsNull() {
        expected.expect(BadRequestException.class);
        expected.expectMessage("Property name is requested");

        PropertyReq propertyReq = PropertyData.mockPropertyReq();
        propertyReq.setName("");

        validator.validateAddProperty(propertyReq);
    }

    @Test
    public void validateApprovedProperty() {
        when(repository.existsById(PropertyData.PROPERTY_ID)).thenReturn(true);

        validator.validateApprovedProperty(PropertyData.PROPERTY_ID, PropertyData.mockPropertyApproveReq());
    }

}
