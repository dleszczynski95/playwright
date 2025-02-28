package tests;

import configuration.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.AssertJUnit;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ListenersTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(ListenersTests.class);

    public ListenersTests() {
        super(TestType.FRONTEND);
    }

    @BeforeMethod
    public void initializePages() {
    }

    @Test
    public void passedTest() {
        logger.info("Checking Pass Test");
    }

    @Test
    public void skippedTest() {
        throw new SkipException("Checking Skip Test");
    }

    @Test(invocationCount = 3)
    public void invocationTest() {
        logger.info("Checking Invocation Test");
    }

    @Test
    public void failedTest() {
        logger.info("Checking Failed Test");
        AssertJUnit.assertEquals(true, false);
    }

    @Test(dataProvider = "test1")
    public void dataProviderTest(String value) {
        logger.info("Checking Data Provider Test");
        AssertJUnit.assertEquals(value, "ABC");
    }

    @DataProvider(name = "test1")
    public Object[][] test1() {
        return new Object[][]{
                {"ABC"}, {"DEF"}, {"GHI"}
        };
    }
}
