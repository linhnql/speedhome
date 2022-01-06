package com.speedhome.poc.service.api;

import com.speedhome.poc.api.TestApi;
import com.speedhome.poc.api.model.Properties;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TestController implements TestApi {

    @Override
    public ResponseEntity<Properties> testAPI() {
        Properties properties = new Properties();
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }
}
