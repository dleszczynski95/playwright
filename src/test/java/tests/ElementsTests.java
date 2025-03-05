package tests;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import configuration.BaseTest;
import configuration.PagesInitializer;
import io.qameta.allure.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ElementsPage;
import pages.MainPage;

import java.util.Arrays;
import java.util.List;

public class ElementsTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(ElementsTests.class);
    public MainPage mainPage;
    public ElementsPage elementsPage;

    public ElementsTests() {
        super(TestType.FRONTEND);
    }

    @BeforeMethod
    public void initializePages() {
        new PagesInitializer(this, page, List.of(MainPage.class));
        elementsPage = mainPage.goTo(MainPage.Options.ELEMENTS);
    }

    @Test
    @Description("Checking main elements")
    public void mainElementsProperlyDisplayed() {
        AssertJUnit.assertEquals(elementsPage.getLeftMenu().getExpandedItem(), MainPage.Options.ELEMENTS.getLabel());
        AssertJUnit.assertEquals(elementsPage.getLeftMenu().getItems(), Arrays.stream(ElementsPage.Items.values()).map(ElementsPage.Items::getUiLabel).toList());
        logger.info("Checked main elements");
    }

    @Test(invocationCount = 2)
    @Description("Checking wrong email")
    public void checkWrongEmail() {
        elementsPage
                .selectItem(ElementsPage.Items.TEXT_BOX)
                .setFullName(config.getFullName())
                .setEmail("wrongEmail.com")
                .setPermanentAddress(config.getPermanentAddress())
                .setCurrentAddress(config.getCurrentAddress())
                .clickSubmit();

        PlaywrightAssertions.assertThat(elementsPage.getLocator(elementsPage.getOutputNameSelector())).isHidden();
        logger.info("Checked wrong email");
    }

    @Test
    @Description("Checking properly email")
    public void checkProperlyEmail() {
        elementsPage
                .selectItem(ElementsPage.Items.TEXT_BOX)
                .setFullName(config.getFullName())
                .setEmail(config.getEmail())
                .setPermanentAddress(config.getPermanentAddress())
                .setCurrentAddress(config.getCurrentAddress())
                .clickSubmit();

        AssertJUnit.assertEquals(elementsPage.getOutputName(), config.getFullName());
        AssertJUnit.assertEquals(elementsPage.getOutputEmail(), config.getEmail());
        AssertJUnit.assertEquals(elementsPage.getOutputCurrentAddress(), config.getCurrentAddress());
        AssertJUnit.assertEquals(elementsPage.getOutputPermanentAddress(), config.getPermanentAddress());
        logger.info("Checked happy path");
    }
}