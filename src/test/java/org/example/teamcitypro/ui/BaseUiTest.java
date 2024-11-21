package org.example.teamcitypro.ui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.example.teamcitypro.BaseTest;
import com.codeborne.selenide.Configuration;
import org.example.teamcitypro.api.config.Config;
import org.example.teamcitypro.api.enums.Endpoint;
import org.example.teamcitypro.api.models.User;
import org.example.teamcitypro.ui.pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import java.util.Map;

public class BaseUiTest extends BaseTest {
    @BeforeSuite(alwaysRun = true)
    public void setupUiTest() {
        Configuration.browser = Config.getProperty("browser");
        Configuration.baseUrl = "http://" + Config.getProperty("host");

        // Don't write UI tests with local browser
        // Do it on remote browser
        Configuration.remote = Config.getProperty("remote");
        Configuration.browserSize = Config.getProperty("browserSize");
        Configuration.browserCapabilities.setCapability("selenoid:options",
                Map.of("enableVNC", true,
                        "enableLog", true));
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(true));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        Selenide.closeWebDriver();
    }

    protected void loginAs(User user) {
        superUserCheckRequests.getRequest(Endpoint.USERS).create(testData.getUser());
        LoginPage.open().login(testData.getUser());
    }
}
