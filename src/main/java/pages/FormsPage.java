package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import helpers.LazyLocator;
import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.components.LeftMenu;

import java.util.List;

@Getter
public class FormsPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(FormsPage.class);

    //region Locators
    protected LazyLocator userForm;
    protected LazyLocator firstNameInput;
    protected LazyLocator lastNameInput;
    protected LazyLocator emailInput;
    protected List<LazyLocator> genderInput;
    protected LazyLocator mobileInput;
    protected LazyLocator dateOfBirthInput;
    protected LazyLocator subjectsContainer;
    protected List<LazyLocator> hobbiesInput;
    protected LazyLocator pictureInput;
    protected LazyLocator currentAddressInput;
    protected LazyLocator stateInput;
    protected LazyLocator cityInput;
    protected LazyLocator submitButton;
    //endregion

    public FormsPage(Page page) {
        super(page);
        initializeLocators();
    }

    public FormsPage setFirstName(String firstName) {
        firstNameInput.fill(firstName);
        return this;
    }

    public FormsPage setLastName(String lastName) {
        lastNameInput.fill(lastName);
        return this;
    }

    public FormsPage setEmail(String email) {
        emailInput.fill(email);
        return this;
    }

    public FormsPage setMobile(String number) {
        mobileInput.fill(number);
        return this;
    }

    public FormsPage setSubjects(List<String> subjects) {
        return this;
    }

    public FormsPage setPicture(String path) {
        return this;
    }

    public FormsPage setCurrentAddress(String address) {
        currentAddressInput.fill(address);
        return this;
    }

    public FormsPage setState(States state) {
        stateInput.click();

        return this;
    }

    public FormsPage setCity(Cities city) {
        return this;
    }

    public FormsPage setGender(Gender gender) {
        page.locator("#genterWrapper .custom-control").filter(new Locator.FilterOptions().setHasText(gender.getUiLabel())).first().click();
        return this;
    }

    public FormsPage setHobbies(Hobbies hobbies) {
        page.locator("#hobbiesWrapper .custom-control").filter(new Locator.FilterOptions().setHasText(hobbies.getUiLabel())).first().click();
        return this;
    }

    public FormsPage setHobbies(List<Hobbies> hobbies) {
        hobbies.forEach(h -> page.locator("#hobbiesWrapper .custom-control").filter(new Locator.FilterOptions().setHasText(h.getUiLabel())).first().click());
        return this;
    }

    @Step("Select practice form")
    public FormsPage selectItem() {
        new LeftMenu(page).selectItem("Practice Form");
        return this;
    }

    @Step("Click submit")
    public FormsPage clickSubmit() {
        submitButton.click();
        userForm.waitForAttribute("class", "was-validated");
        return this;
    }

    protected void initializeLocators() {
        this.userForm = new LazyLocator(() -> page.locator("#userForm"), page);
        this.firstNameInput = new LazyLocator(() -> page.locator("#firstName"), page);
        this.lastNameInput = new LazyLocator(() -> page.locator("#lastName"), page);
        this.emailInput = new LazyLocator(() -> page.locator("#userEmail"), page);
        this.mobileInput = new LazyLocator(() -> page.locator("#userNumber"), page);
        this.dateOfBirthInput = new LazyLocator(() -> page.locator("#dateOfBirthInput"), page);
        this.subjectsContainer = new LazyLocator(() -> page.locator("#subjectsContainer"), page);
        this.pictureInput = new LazyLocator(() -> page.locator("#uploadPicture"), page);
        this.currentAddressInput = new LazyLocator(() -> page.locator("#currentAddress"), page);
        this.stateInput = new LazyLocator(() -> page.locator("#state"), page);
        this.cityInput = new LazyLocator(() -> page.locator("#lastName"), page);
        this.submitButton = new LazyLocator(() -> page.locator("#submit"), page);
        this.stateInput = new LazyLocator(() -> page.locator("#state"), page);
    }

    @AllArgsConstructor
    @Getter
    public enum Gender {
        MALE("Male"), FEMALE("Female"), OTHER("Other");

        private final String uiLabel;
    }

    @AllArgsConstructor
    @Getter
    public enum Hobbies {
        SPORTS("Sports"), READING("Reading"), MUSIC("Music");

        private final String uiLabel;
    }

    @AllArgsConstructor
    @Getter
    public enum States {
        NCR("NCR", List.of(Cities.DELHI, Cities.GURGAON, Cities.NOIDA)),
        UTTAR_PRADESH("Uttar Pradesh", List.of(Cities.AGRA, Cities.LUCKNOW, Cities.MERRUT)),
        HARYANA("Haryana", List.of(Cities.KARNAL, Cities.PANIPAT)),
        RAJASTHAN("Rajasthan", List.of(Cities.JAIPUR, Cities.JAISELMER));

        private final String uiLabel;
        private final List<Cities> cities;
    }

    @AllArgsConstructor
    @Getter
    public enum Cities {
        DELHI("Delhi"), GURGAON("Gurgaon"), NOIDA("Noida"),
        AGRA("Agra"), LUCKNOW("Lucknow"), MERRUT("Merrut"),
        KARNAL("Karnal"), PANIPAT("Panipat"),
        JAIPUR("Jaipur"), JAISELMER("Jaiselmer");

        private final String uiLabel;
    }
}
