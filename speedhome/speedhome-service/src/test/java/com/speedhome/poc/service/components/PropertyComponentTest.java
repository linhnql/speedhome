package com.speedhome.poc.service.components;

import com.speedhome.poc.client.api.PropertiesApi;
import com.speedhome.poc.client.handler.ApiClient;
import com.speedhome.poc.client.model.Property;
import com.speedhome.poc.client.model.PropertyApproveReq;
import com.speedhome.poc.client.model.PropertyReq;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@TestPropertySource("/application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertyComponentTest {
    private final String API_KEY = "691c5597-e7d2-4c06-af49-f9369b367783";

    private static final String HTTP_LOCALHOST = "http://localhost:";
    private static final String BASE_PATH = "/speedhome/backend/v1";

    public static final String PROPERTY_ID = "c73095df-a31d-45a0-8d57-8c43108830c7";
    public static final String NAME = "Old Property";
    public static final String DESC = "This is a beautiful property";
    public static final double PRICE = 1500.0;
    public static final String UPDATE_USER_ID = "khoavu";

    private PropertiesApi api;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        String url = HTTP_LOCALHOST + port + BASE_PATH;
        ApiClient client = new ApiClient();
        client.setBasePath(url);
        api = new PropertiesApi(client);
    }

    @Test
    public void ensurePropertyCreated() {
        Property property = api.addProperty(API_KEY, mockPropertyReq());

        assertProperty(property);
    }

    @Test
    public void ensurePropertyCreateThrowExceptionWhenRequiredFieldNotPass() {
        thrown.expect(HttpClientErrorException.class);

        PropertyReq propertyReq = mockPropertyReq();
        propertyReq.setName("");

        api.addProperty(API_KEY, propertyReq);
    }

    @Test
    public void ensurePropertyUpdated() {
        Property property = api.updateProperty(API_KEY, PROPERTY_ID, mockPropertyReq());

        assertProperty(property);
    }

    @Test
    public void ensurePropertyUpdateThrowExceptionWhenWrongPassed() {
        thrown.expect(HttpClientErrorException.class);

        PropertyReq propertyReq = mockPropertyReq();
        propertyReq.setName("");

        api.updateProperty(API_KEY, PROPERTY_ID + "FAKE", propertyReq);
    }

    private void assertProperty(Property actual) {
        assertThat(actual.getId() != null, is(true));
        assertThat(actual.getName(), is(NAME));
        assertThat(actual.getDesc(), is(DESC));
        assertThat(actual.getPrice(), is(PRICE));
        assertThat(actual.getType().name(), is(Property.TypeEnum.AGRICULTURE.name()));
        assertThat(actual.getStatus().name(), is(Property.StatusEnum.PENDING.name()));
    }

    private PropertyReq mockPropertyReq() {
        PropertyReq propertyReq = new PropertyReq();

        propertyReq.setName(NAME);
        propertyReq.setDesc(DESC);
        propertyReq.setPrice(PRICE);
        propertyReq.setType(PropertyReq.TypeEnum.AGRICULTURE);
        propertyReq.setStatus(PropertyReq.StatusEnum.PENDING);
        propertyReq.setUserId(UPDATE_USER_ID);

        return propertyReq;
    }
}
