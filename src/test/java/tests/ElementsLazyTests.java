package tests;

import configuration.BaseTest;
import configuration.PagesInitializer;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ElementsLazyPage;
import pages.ElementsPage;
import pages.MainPage;

import java.util.List;

public class ElementsLazyTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(ElementsLazyTests.class);
    public MainPage mainPage;
    public ElementsLazyPage elementsPage;

    public ElementsLazyTests() {
        super(TestType.FRONTEND);
    }

    @BeforeMethod
    public void initializePages() {
        new PagesInitializer(this, page, List.of(MainPage.class));
        elementsPage = mainPage.goTo(MainPage.Options.ELEMENTS_LAZY);
    }

    @Test
    public void mainElementsProperlyDisplayed() {
        AssertJUnit.assertEquals(elementsPage.getLeftMenu().getExpandedItem(), MainPage.Options.ELEMENTS.getLabel());
        AssertJUnit.assertEquals(elementsPage.getLeftMenu().getItems(), ElementsPage.Items.VALUES.stream().map(ElementsPage.Items::getUiLabel).toList());
        logger.info("Checked main elements");
    }

    @Test(invocationCount = 2)
    @Description("Checking wrong email")
    public void checkWrongEmail() {
        elementsPage
                .selectItem(ElementsLazyPage.Items.TEXT_BOX)
                .setFullName(config.getFullName())
                .setEmail("wrongEmail.com")
                .setPermanentAddress(config.getPermanentAddress())
                .setCurrentAddress(config.getCurrentAddress())
                .clickSubmit();

        AssertJUnit.assertTrue(elementsPage.getFullNameOutput().isHidden());
        logger.info("Checked wrong email");
    }

    @Test
    public void checkProperlyEmail() {
        elementsPage
                .selectItem(ElementsLazyPage.Items.TEXT_BOX)
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
