package tests;

import configuration.BaseTest;
import configuration.PagesInitializer;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.FormsPage;
import pages.MainPage;

import java.util.List;

public class FormsTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(FormsTests.class);
    public MainPage mainPage;
    public FormsPage formsPage;
    private static final String INVALID_COLOR = "rgb(220, 53, 69)";
    private static final String VALID_COLOR = "rgb(40, 167, 69)";

    public FormsTests() {
        super(TestType.FRONTEND);
    }

    @BeforeMethod
    public void initializePages() {
        new PagesInitializer(this, page, List.of(MainPage.class));
        formsPage = mainPage.goTo(MainPage.Options.FORMS);
        formsPage.selectItem();
    }

    @Step("Checking empty form submit")
    @Test
    public void submitEmptyForm() {
        formsPage.clickSubmit();
        page.waitForCondition(() -> formsPage.getFirstNameInput().getComputedStyle("borderColor").equals(INVALID_COLOR));
        AssertJUnit.assertEquals(INVALID_COLOR, formsPage.getFirstNameInput().getComputedStyle("borderColor"));
        AssertJUnit.assertEquals(INVALID_COLOR, formsPage.getLastNameInput().getComputedStyle("borderColor"));
        AssertJUnit.assertEquals(INVALID_COLOR, formsPage.getMobileInput().getComputedStyle("borderColor"));
        AssertJUnit.assertEquals(VALID_COLOR, formsPage.getEmailInput().getComputedStyle("borderColor"));
        AssertJUnit.assertEquals(VALID_COLOR, formsPage.getDateOfBirthInput().getComputedStyle("borderColor"));
        AssertJUnit.assertEquals(VALID_COLOR, formsPage.getCurrentAddressInput().getComputedStyle("borderColor"));
    }

    @Step("Checking full form submit")
    @Test
    public void submitFullForm() {
        formsPage
                .setFirstName("name")
                .setLastName("name2")
                .setEmail("mail@mail.com")
                .setGender(FormsPage.Gender.MALE)
                .setHobbies(List.of(FormsPage.Hobbies.MUSIC, FormsPage.Hobbies.READING))
                .setMobile("1234567890");
        formsPage.clickSubmit();
    }
}