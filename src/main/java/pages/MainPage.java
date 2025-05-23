package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.List;

public class MainPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(MainPage.class);
    protected static final String CARDS_SELECTOR = ".category-cards .card-body";

    public MainPage(Page page) {
        super(page);
        waitForPage();
    }

    public List<String> getListOfCards() {
        Locator cards = page.locator(CARDS_SELECTOR);
        return cards.all().stream().map(Locator::textContent).toList();
    }

    @Step("Go to {}")
    public <T> T goTo(Options option) {
        page.locator(CARDS_SELECTOR).filter(new Locator.FilterOptions().setHasText(option.label)).click();
        try {
            Constructor<?> constructor = option.getClazz().getConstructor(Page.class);
            return (T) constructor.newInstance(page);
        } catch (Exception e) {
            throw new RuntimeException("Can't create instance: " + option.getClazz().getName(), e);
        }
    }

    public void waitForPage() {
        page.waitForCondition(() -> page.locator(CARDS_SELECTOR).count() == 6, timeout);
        logger.info("User should be on main page");
    }

    @AllArgsConstructor
    @Getter
    public enum Options {
        ELEMENTS("Elements", ElementsPage.class),
        ELEMENTS_LAZY("Elements", ElementsLazyPage.class),
        FORMS("Forms", FormsPage.class),
        ALERTS_FRAME_WINDOWS("Alerts, Frame & Windows", AlertsPage.class),
        WIDGETS("Widgets", WidgetsPage.class),
        INTERACTIONS("Interactions", InteractionsPage.class),
        BOOK_STORE("Book Store Application", BookStorePage.class);

        private final String label;
        private final Class<?> clazz;
    }
}