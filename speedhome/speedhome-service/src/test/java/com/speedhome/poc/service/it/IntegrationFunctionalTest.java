package com.speedhome.poc.service.it;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.ClassRule;
import org.junit.runner.RunWith;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty",
    "html:target/cucumber",
    "json:target/cucumber/cucumber.json",
    "junit:target/cucumber/cucumber.xml" },
    features = "src/test/resources/gherkin",
    strict = true)
public class IntegrationFunctionalTest {
    private static final int CONTAINER_THREADS = 20;
    private static final int PORT_NUMBER = 9600;

    @ClassRule
    public static final WireMockClassRule wiremock =
            new WireMockClassRule(wireMockConfig().containerThreads(CONTAINER_THREADS).port(PORT_NUMBER));
}
