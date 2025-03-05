package helpers;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class LazyLocator {
    private final Supplier<Locator> supplier;
    private final Locator locator;
    private final Page page;
    private final Page.WaitForConditionOptions timeout;

    public LazyLocator(Supplier<Locator> supplier, Page page) {
        this.supplier = supplier;
        this.locator = supplier.get();
        this.page = page;
        timeout = new Page.WaitForConditionOptions().setTimeout(5000);
    }

    public void click() {
        page.waitForCondition(this::isVisible);
        locator.click();
    }

    public void fill(String text) {
        page.waitForCondition(this::isVisible);
        locator.fill(text);
    }

    public void fill(long text) {
        page.waitForCondition(this::isVisible);
        locator.fill(String.valueOf(text));
    }

    public boolean isVisible() {
        return locator.isVisible();
    }

    public boolean isHidden() {
        return locator.isHidden();
    }

    public void press(String key) {
        page.waitForCondition(this::isVisible);
        locator.press(key);
    }

    public String getAttribute(String name) {
        return locator.getAttribute(name);
    }

    public void waitForAttribute(String attribute, String value) {
        page.waitForCondition(() -> locator.getAttribute(attribute).equals(value), timeout);
    }

    public String getText() {
        return locator.textContent();
    }

    public String getComputedStyle(String cssValue) {
        return locator.evaluate("el => getComputedStyle(el)." + cssValue).toString();
    }
}
