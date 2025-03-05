package configuration;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import config.Config;
import config.YamlReader;
import io.qameta.allure.testng.AllureTestNg;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.MainPage;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@Listeners({Listener.class, AllureTestNg.class})
public abstract class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected Config config;
    protected static Playwright playwright;
    protected static Browser browser;
    @Getter
    protected static TestType testType;
    protected Page page;

    public BaseTest(TestType testType) {
        BaseTest.testType = testType;
    }

    public void setPage(ITestResult result, Page page) {
        result.setAttribute("page", page);
    }

    @BeforeTest
    public void setUp() throws IOException {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        FileUtils.cleanDirectory(new File("allure-results"));

        YamlReader yamlReader = new YamlReader("config.yaml");
        config = yamlReader.getActiveConfig();
        logger.info(config.getConfigLog());
        playwright = Playwright.create();
        if (!testType.equals(TestType.BACKEND)) setBrowser();
    }

    @BeforeMethod
    public void setupTest(ITestResult result) {
        if (browser != null) {
            logger.info("Setup Page");
            page = browser.newPage();
            setPage(result, page);
            page.navigate(config.getUrl());
            new MainPage(page);
            result.setAttribute("page", page);
        }
    }

    @AfterMethod
    public void tearDownTest() {
        if (page != null) {
            page.close();
        }

    }

    @AfterTest
    static void tearDown() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    private void setBrowser() {
        switch (config.getBrowserName()) {
            case FIREFOX -> browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
            case CHROME -> browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }
    }

    public enum TestType {
        FRONTEND, BACKEND, HYBRID
    }
}
