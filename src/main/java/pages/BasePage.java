package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePage {
    private final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected Page page;
    Page.WaitForConditionOptions timeout;

    protected BasePage(Page page) {
        this.page = page;
        page.setDefaultTimeout(5000);
        timeout = new Page.WaitForConditionOptions().setTimeout(5000);
    }

    public void click(String locator) {
        getLocator(locator).click();
        logger.info("Clicked on locator: {}", locator);
    }

    public void clickWithText(String locator, String text) {
        getLocator(locator).filter(new Locator.FilterOptions().setHasText(text)).click();
        logger.info("Clicked on locator: {}, with name: {}", locator, text);
    }

    public Locator getLocator(String locator) {
        return page.locator(locator);
    }

    public Locator getLocator(String locator, String text) {
        return page.locator(locator).filter(new Locator.FilterOptions().setHasText(text));
    }

    public void fillInput(String locator, String value) {
        getLocator(locator).fill(value);
        logger.info("Fill {} with value: {}", locator, value);
    }

    public String getText(String locator) {
        return getLocator(locator).textContent();
    }
}
