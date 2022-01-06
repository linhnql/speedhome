package com.speedhome.poc.service.api;

import com.speedhome.poc.api.PropertiesApi;
import com.speedhome.poc.api.model.Properties;
import com.speedhome.poc.api.model.Property;
import com.speedhome.poc.api.model.PropertyApproveReq;
import com.speedhome.poc.api.model.PropertyReq;
import com.speedhome.poc.service.search.HibernateSearchUtil;
import com.speedhome.poc.service.service.PropertyService;
import com.speedhome.poc.service.validator.PropertySearchValidator;
import com.speedhome.poc.service.validator.PropertyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/speedhome/backend/v1")
@CrossOrigin
public class PropertyController implements PropertiesApi {

    private final PropertyService service;

    private final PropertyValidator validator;

    private final PropertySearchValidator searchValidator;

    @Autowired
    public PropertyController(PropertyService service,
                              PropertyValidator validator,
                              PropertySearchValidator searchValidator) {
        this.service = service;
        this.validator = validator;
        this.searchValidator = searchValidator;
    }


    @Override
    public ResponseEntity<Property> addProperty(@RequestHeader(value="apikey") String apikey,
                                                @RequestBody PropertyReq request) {
        validator.validateAddProperty(request);

        Property property = service.createProperty(request);

        return new ResponseEntity<>(property, HttpStatus.OK);
    }



    @Override
    public ResponseEntity<Property> updateProperty(
            @RequestHeader(value="apikey") String apikey,
            @PathVariable("id") String id,
            @RequestBody PropertyReq request) {
        validator.validateUpdateProperty(id, request);

        Property property = service.updateProperty(id, request);

        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Property> approveProperty(
            @RequestHeader(value="apikey") String apikey,
            @PathVariable("id") String id,
            @RequestBody PropertyApproveReq request) {
        validator.validateApprovedProperty(id, request);

        Property property = service.approveProperty(id, request);

        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Properties> search(
            @RequestHeader(value="apikey") String apikey,
            @NotNull String term) {

        term = HibernateSearchUtil.decodeUrl(term);

        searchValidator.validateTermSearch(term);

        Properties properties = service.search(term);

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

}
