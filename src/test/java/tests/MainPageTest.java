package tests;

import configuration.BaseTest;
import configuration.PagesInitializer;
import io.qameta.allure.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.MainPage;

import java.util.List;

public class MainPageTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(MainPageTest.class);
    public MainPage mainPage;

    public MainPageTest() {
        super(TestType.FRONTEND);
    }

    @BeforeMethod
    public void initializePages() {
        new PagesInitializer(this, page, List.of(MainPage.class));
    }

    @Test
    @Description("Checking main elements")
    public void checkMainElements() {
        AssertJUnit.assertEquals(mainPage.getListOfCards(), List.of("Elements", "Forms", "Alerts, Frame & Windows",
                "Widgets", "Interactions", "Book Store Application"));
        logger.info("Checked list of cards");
    }
}
