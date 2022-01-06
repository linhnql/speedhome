package com.speedhome.poc.service.it.steps;

import com.speedhome.poc.client.api.PropertiesApi;
import com.speedhome.poc.client.model.PropertyReq;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.web.client.RestClientException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PropertySteps extends CommonStepConfiguration {

    protected PropertiesApi propertyApi;

    private RestClientException restClientException;

    private PropertyReq propertyReq;

    private final String API_KEY = "691c5597-e7d2-4c06-af49-f9369b367783";

    @Before
    public void setup() {
        super.setup();
        propertyApi = new PropertiesApi(baseApiClient);
    }

    @Given("^user request to create property with \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void userRequestToCreatePropertyWith(String name, String decs, String type, String price) {
        propertyReq = createPropertyReq(name, decs, type, price);
    }

    @When("^the user do action create property$")
    public void theUserDoActionCreateProperty() {
        try {
            propertyApi.addProperty(API_KEY, propertyReq);
        } catch (RestClientException e) {
            restClientException = e;
        }
    }

    @Then("^user action is successful with \"([^\"]*)\"$")
    public void userActionIsSuccessfulWith(String code) {
        assertThat(propertyApi.getApiClient().getStatusCode().value(), is(Integer.valueOf(code)));
    }

    @Then("^user action is failed with \"([^\"]*)\"$")
    public void userActionIsFailedWith(String code) {
        assertThat(getExceptionStatusCode(restClientException), is(code));
    }

    private PropertyReq createPropertyReq(String name, String desc, String type, String price) {
        PropertyReq propertyReq = new PropertyReq();

        propertyReq.setName(name);
        propertyReq.setDesc(desc);
        propertyReq.setType(PropertyReq.TypeEnum.fromValue(type));
        propertyReq.setPrice(Double.parseDouble(price));

        return propertyReq;
    }
}
