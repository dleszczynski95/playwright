package configuration;

import com.microsoft.playwright.Page;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public class PagesInitializer {

    public PagesInitializer(Object targetObject, Page page, List<Class<?>> pageClasses) {
        for (Class<?> pageClass : pageClasses) {
            try {
                Constructor<?> constructor = pageClass.getConstructor(Page.class);

                Object instance = constructor.newInstance(page);

                for (Field field : targetObject.getClass().getDeclaredFields()) {
                    if (field.getType().equals(pageClass)) {
                        field.setAccessible(true);
                        field.set(targetObject, instance);
                        break;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Class can't be initialized found: " + pageClass.getName(), e);
            }
        }
    }
}
