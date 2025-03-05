package pages;

import com.microsoft.playwright.Page;
import helpers.LazyLocator;
import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.components.LeftMenu;

@Getter
public class ElementsLazyPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(ElementsLazyPage.class);

    protected LazyLocator fullNameInput;
    protected LazyLocator emailInput;
    protected LazyLocator currentAddressInput;
    protected LazyLocator permanentAddressInput;
    protected LazyLocator submitButton;
    protected LazyLocator fullNameOutput;
    protected LazyLocator emailOutput;
    protected LazyLocator currentAddressOutput;
    protected LazyLocator permanentAddressOutput;

    private final LeftMenu leftMenu;

    public ElementsLazyPage(Page page) {
        super(page);
        initializeLocators();
        leftMenu = new LeftMenu(page);
        logger.info("User is on Elements Page");
    }

    @Step("Select item {0}")
    public ElementsLazyPage selectItem(Items item) {
        leftMenu.selectItem(item.getUiLabel());
        return this;
    }

    @Step("Set full name {0}")
    public ElementsLazyPage setFullName(String fullName) {
        fullNameInput.fill(fullName);
        return this;
    }

    @Step("Set email {0}")
    public ElementsLazyPage setEmail(String email) {
        emailInput.fill(email);
        return this;
    }

    @Step("Set current address {0}")
    public ElementsLazyPage setCurrentAddress(String address) {
        currentAddressInput.fill(address);
        return this;
    }

    @Step("Set permanent address {0}")
    public ElementsLazyPage setPermanentAddress(String address) {
        permanentAddressInput.fill(address);
        return this;
    }

    @Step("Click submit")
    public ElementsLazyPage clickSubmit() {
        submitButton.click();
        return this;
    }

    public String getOutputName() {
        return fullNameOutput.getText().split(":")[1].strip();
    }

    public String getOutputEmail() {
        return emailOutput.getText().split(":")[1].strip();
    }

    public String getOutputCurrentAddress() {
        return currentAddressOutput.getText().split(":")[1].strip();
    }

    public String getOutputPermanentAddress() {
        return permanentAddressOutput.getText().split(":")[1].strip();
    }

    protected void initializeLocators() {
        this.fullNameInput = new LazyLocator(() -> page.locator("#userName"), page);
        this.emailInput = new LazyLocator(() -> page.locator("#userEmail"), page);
        this.currentAddressInput = new LazyLocator(() -> page.locator("#currentAddress"), page);
        this.permanentAddressInput = new LazyLocator(() -> page.locator("#permanentAddress"), page);
        this.submitButton = new LazyLocator(() -> page.locator("#submit"), page);

        this.fullNameOutput = new LazyLocator(() -> page.locator("#output #name"), page);
        this.emailOutput = new LazyLocator(() -> page.locator("#output #email"), page);
        this.currentAddressOutput = new LazyLocator(() -> page.locator("#output #currentAddress"), page);
        this.permanentAddressOutput = new LazyLocator(() -> page.locator("#output #permanentAddress"), page);
    }

    @AllArgsConstructor
    @Getter
    public enum Items {
        TEXT_BOX("Text Box"), CHECK_BOX("Check Box"), RADIO_BUTTON("Radio Button"),
        WEB_TABLES("Web Tables"), BUTTONS("Buttons"), LINKS("Links"),
        BROKEN_LINKS("Broken Links - Images"), UPLOAD_DOWNLOAD("Upload and Download"),
        DYNAMIC_PROPERTIES("Dynamic Properties");

        private final String uiLabel;
    }
}