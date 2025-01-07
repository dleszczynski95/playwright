package pages;

import com.microsoft.playwright.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.components.LeftMenu;

import java.util.List;

@Getter
public class ElementsPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(ElementsPage.class);

    protected String fullNameSelector = "#userName";
    protected String emailSelector = "#userEmail";
    protected String currentAddressSelector = "#currentAddress";
    protected String permanentAddressSelector = "#permanentAddress";
    protected String submitSelector = "#submit";
    protected String outputNameSelector = "#output #name";
    protected String outputEmailSelector = "#output #email";
    protected String outputCurrentAddressSelector = "#output #currentAddress";
    protected String outputPermanentAddressSelector = "#output #permanentAddress";

    private final LeftMenu leftMenu;

    public ElementsPage(Page page) {
        super(page);
        leftMenu = new LeftMenu(page);
        logger.info("User is on Elements Page");
    }

    public ElementsPage selectItem(Items item) {
        leftMenu.selectItem(item.getUiLabel());
        return this;
    }

    public ElementsPage setFullName(String fullName) {
        fillInput(fullNameSelector, fullName);
        return this;
    }

    public ElementsPage setEmail(String email) {
        fillInput(emailSelector, email);
        return this;
    }

    public ElementsPage setCurrentAddress(String address) {
        fillInput(currentAddressSelector, address);
        return this;
    }

    public ElementsPage setPermanentAddress(String address) {
        fillInput(permanentAddressSelector, address);
        return this;
    }

    public ElementsPage clickSubmit() {
        click(submitSelector);
        return this;
    }

    public String getOutputName() {
        return getText(outputNameSelector).split(":")[1].strip();
    }

    public String getOutputEmail() {
        return getText(outputEmailSelector).split(":")[1].strip();
    }

    public String getOutputCurrentAddress() {
        return getText(outputCurrentAddressSelector).split(":")[1].strip();
    }

    public String getOutputPermanentAddress() {
        return getText(outputPermanentAddressSelector).split(":")[1].strip();
    }


    @AllArgsConstructor
    @Getter
    public enum Items {
        TEXT_BOX("Text Box"), CHECK_BOX("Check Box"), RADIO_BUTTON("Radio Button"),
        WEB_TABLES("Web Tables"), BUTTONS("Buttons"), LINKS("Links"),
        BROKEN_LINKS("Broken Links - Images"), UPLOAD_DOWNLOAD("Upload and Download"),
        DYNAMIC_PROPERTIES("Dynamic Properties");

        private final String uiLabel;
        public final static List<Items> VALUES = List.of(values());
    }
}
