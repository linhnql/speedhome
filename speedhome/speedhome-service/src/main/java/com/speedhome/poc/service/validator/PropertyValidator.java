package com.speedhome.poc.service.validator;

import com.speedhome.poc.api.model.PropertyApproveReq;
import com.speedhome.poc.api.model.PropertyReq;
import com.speedhome.poc.service.exception.BadRequestException;
import com.speedhome.poc.service.exception.EntityNotFoundException;
import com.speedhome.poc.service.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropertyValidator {
    private static final String PROPERTY_DOES_NOT_EXIST = "Property does not exist";

    private static final String NAME_REQUEST = "Property name is requested";

    private static final String STATUS_COMMENT_REQUEST = "Status comment is required";

    private final PropertyRepository repository;

    @Autowired
    public PropertyValidator(PropertyRepository repository) {
        this.repository = repository;
    }

    private void validatePropertyExist(String id) {
        if (repository.existsById(id)) {return;}

        throw new EntityNotFoundException(PROPERTY_DOES_NOT_EXIST);
    }

    public void validateAddProperty(PropertyReq request) {
        checkRequiredField(request.getName(), NAME_REQUEST);
    }

    public void validateUpdateProperty(String id, PropertyReq request) {
        validatePropertyExist(id);
        checkRequiredField(request.getName(), NAME_REQUEST);
    }

   public void validateApprovedProperty(String id, PropertyApproveReq request) {
        validatePropertyExist(id);
        checkRequiredField(request.getStatusComment(), STATUS_COMMENT_REQUEST);
    }

    private void checkRequiredField(String value, String errorMsg) {
        if (null == value || value.trim().isEmpty()) {
            throw new BadRequestException(errorMsg);
        }
    }

}
