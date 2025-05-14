package helpers;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.lang.reflect.Field;

public class PlaywrightDecorator {
    public static void initElements(Page page, Object pageObject) {
        for (Field field : pageObject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(FindBy.class) && field.getType() == Locator.class) {
                FindBy findBy = field.getAnnotation(FindBy.class);

                Locator locator = page.locator(findBy.css());

                field.setAccessible(true);
                try {
                    field.set(pageObject, locator);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to inject locator");
                }
            }
        }
    }
}
