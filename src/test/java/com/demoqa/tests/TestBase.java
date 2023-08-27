package com.demoqa.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.demoqa.configs.WebConfig;
import com.demoqa.helpers.Attach;
import com.demoqa.pages.AutomationPracticePage;
import com.demoqa.utils.TestData;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;


public class TestBase {

    static WebConfig config = ConfigFactory.create(WebConfig.class, System.getProperties());
    AutomationPracticePage automationPracticePage = new AutomationPracticePage();
    TestData testData = new TestData();
    @BeforeAll
    static void beforeAll() {
//        WebDriverManager.chromedriver().clearDriverCache().setup();

        Configuration.browser = config.browser();
        Configuration.browserVersion = config.browserVersion();
        Configuration.remote = config.isRemote() ? config.remoteUrl() : null;

        Configuration.baseUrl = config.baseUrl();
        Configuration.browserSize = config.browserSize();
        Configuration.holdBrowserOpen = config.holdBrowserOpen();
        Configuration.pageLoadStrategy = config.pageLoadStrategy();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));

        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void addListener(){
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments () {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

}
