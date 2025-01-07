package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.List;

public class MainPage extends BasePage {
    private final static Logger logger = LoggerFactory.getLogger(MainPage.class);
    protected final String cardsSelector = ".category-cards .card-body";

    public MainPage(Page page) {
        super(page);
        waitForPage();
    }

    public List<String> getListOfCards() {
        Locator cards = page.locator(cardsSelector);
        return cards.all().stream().map(Locator::textContent).toList();
    }

    public <T> T goTo(Options option) {
        page.locator(cardsSelector).filter(new Locator.FilterOptions().setHasText(option.label)).click();
        try {
            Constructor<?> constructor = option.getClazz().getConstructor(Page.class);
            return (T) constructor.newInstance(page);
        } catch (Exception e) {
            throw new RuntimeException("Can't create instance: " + option.getClazz().getName(), e);
        }
    }

    public void waitForPage() {
        page.waitForCondition(() -> page.locator(cardsSelector).count() == 6, timeout);
        logger.info("User should be on main page");
    }

    @AllArgsConstructor
    @Getter
    public enum Options {
        ELEMENTS("Elements", ElementsPage.class),
        FORMS("Forms", FormsPage.class),
        ALERTS_FRAME_WINDOWS("Alerts, Frame & Windows", AlertsPage.class),
        WIDGETS("Widgets", WidgetsPage.class),
        INTERACTIONS("Interactions", InteractionsPage.class),
        BOOK_STORE("Book Store Application", BookStorePage.class);

        private final String label;
        private final Class<?> clazz;
        public static final List<Options> VALUES = List.of(values());
    }
}
