package pages.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.BasePage;

import java.util.List;

public class LeftMenu extends BasePage {
    private final Logger logger = LoggerFactory.getLogger(LeftMenu.class);
    protected String itemsListSelector = ".show li.btn";
    protected String elementGroupSelector = ".element-group";

    public LeftMenu(Page page) {
        super(page);
    }

    @Step("Select item {1}")
    public void selectItem(String itemName) {
        logger.info("Select item {}", itemName);
        clickWithText(itemsListSelector, itemName);
    }

    public String getExpandedItem() {
        return getLocator(elementGroupSelector)
                .filter(new Locator.FilterOptions().setHas(getLocator(itemsListSelector)))
                .locator(".header-text")
                .textContent();
    }

    public List<String> getItems() {
        return getLocator(itemsListSelector).all().stream().map(Locator::textContent).toList();
    }
}