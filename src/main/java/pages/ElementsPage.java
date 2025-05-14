package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import helpers.FindBy;
import helpers.PlaywrightDecorator;
import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.components.LeftMenu;

@Getter
public class ElementsPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(ElementsPage.class);

    @FindBy(css = "#userName")
    protected Locator fullNameElement;

    @FindBy(css = "#userEmail")
    protected Locator emailElement;

    @FindBy(css = "#currentAddress")
    protected Locator currentAddressElement;

    @FindBy(css = "#permanentAddress")
    protected Locator permanentAddressElement;

    @FindBy(css = "#submit")
    protected Locator submitElement;

    @FindBy(css = "#output #name")
    protected Locator outputNameElement;

    @FindBy(css = "#output #email")
    protected Locator outputEmailElement;

    @FindBy(css = "#output #currentAddress")
    protected Locator outputCurrentAddressElement;

    @FindBy(css = "#output #permanentAddress")
    protected Locator outputPermanentAddressElement;

    private final LeftMenu leftMenu;

    public ElementsPage(Page page) {
        super(page);
        PlaywrightDecorator.initElements(page, this);
        leftMenu = new LeftMenu(page);
        logger.info("User is on Elements Page");
    }

    @Step("Select item {0}")
    public ElementsPage selectItem(Items item) {
        leftMenu.selectItem(item.getUiLabel());
        return this;
    }

    @Step("Set full name {0}")
    public ElementsPage setFullName(String fullName) {
        fillInput(fullNameElement, fullName);
        return this;
    }

    @Step("Set email {0}")
    public ElementsPage setEmail(String email) {
        fillInput(emailElement, email);
        return this;
    }

    @Step("Set current address {0}")
    public ElementsPage setCurrentAddress(String address) {
        fillInput(currentAddressElement, address);
        return this;
    }

    @Step("Set permanent address {0}")
    public ElementsPage setPermanentAddress(String address) {
        fillInput(permanentAddressElement, address);
        return this;
    }

    @Step("Click submit")
    public ElementsPage clickSubmit() {
        click(submitElement);
        return this;
    }

    public String getOutputName() {
        return getText(outputNameElement).split(":")[1].strip();
    }

    public String getOutputEmail() {
        return getText(outputEmailElement).split(":")[1].strip();
    }

    public String getOutputCurrentAddress() {
        return getText(outputCurrentAddressElement).split(":")[1].strip();
    }

    public String getOutputPermanentAddress() {
        return getText(outputPermanentAddressElement).split(":")[1].strip();
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