package org.example.teamcitypro.api;

import org.example.teamcitypro.api.generators.TestDataStorage;
import org.example.teamcitypro.api.models.TestData;
import org.example.teamcitypro.api.requests.CheckedRequests;
import org.example.teamcitypro.api.spec.Specifications;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import static org.example.teamcitypro.api.generators.TestDataGenerator.generate;

public class BaseTest {
    protected SoftAssert softy;
    protected CheckedRequests superUserCheckRequests = new CheckedRequests(Specifications.superUserSpec());
    protected TestData testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        softy = new SoftAssert();
        testData = generate();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        softy.assertAll();
        TestDataStorage.getStorage().deleteCreatedEntities();
    }
}
