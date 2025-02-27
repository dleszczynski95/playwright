package configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Listener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(Listener.class);
    private String testName;
    private List<TestObject> testList;
    public static final String PASS_SIGN = "\u2714";
    public static final String FAIL_SIGN = "\u274C";
    public static final String SKIP_SIGN = "\u3030";
    private static final String RUN_SIGN = "\uD83D\uDE80";
    private static final String RUN = "\u001B[32m[" + RUN_SIGN + " RUN]";
    private static final String PASSED = "\u001B[32m[" + PASS_SIGN + " PASSED]";
    private static final String FAILED = "\u001B[31m[" + FAIL_SIGN + " FAILED]";
    private static final String IGNORED = "\u001B[33m[" + SKIP_SIGN + " IGNORED]";
    public static final String RESET = "\u001B[0m";
    private int providerIndex = 1;


    @Override
    public void onTestStart(ITestResult result) {
        int providerSize = 0;
        try {
            providerSize = getProviderSize(result.getMethod().getConstructorOrMethod().getMethod());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        boolean hasDataProvider = providerSize > 0;
        if (hasDataProvider) {
            testName = result.getMethod().getMethodName() + "#" + providerIndex;
            providerIndex = providerIndex == providerSize ? 1 : providerIndex + 1;
        } else {
            int currentInvocation = result.getMethod().getCurrentInvocationCount();
            int totalInvocations = result.getMethod().getInvocationCount();
            testName = result.getMethod().getMethodName() + (totalInvocations > 1 ? "#" + (currentInvocation + 1) : "");
        }
        logger.info("{} Starting test: {}{}", RUN, testName, RESET);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("{} Test: {}{}", PASSED, testName, RESET);
        testList.stream().filter(t -> t.getTestName().equals(testName)).findFirst().orElseThrow().setTestResult(TestObject.TestResult.PASSED);
        printTestsStatus();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info("{} Test: {}{}", FAILED, testName, RESET);
        testList.stream().filter(t -> t.getTestName().equals(testName)).findFirst().orElseThrow().setTestResult(TestObject.TestResult.FAILED);
        printTestsStatus();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("{} Test: {}{}", IGNORED, testName, RESET);
        testList.stream().filter(t -> t.getTestName().equals(testName)).findFirst().orElseThrow().setTestResult(TestObject.TestResult.SKIPPED);
        printTestsStatus();
    }

    @Override
    public void onStart(ITestContext context) {
        try {
            getAllMethods(context);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            getAllMethods(context);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void printTestsStatus() {
        logger.info("                Test Progress:");
        System.out.println("            -----------------------------");
        testList.forEach(System.out::println);
        System.out.println("            -----------------------------");
    }

    private void getAllMethods(ITestContext context) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        testList = new ArrayList<>();
        for (ITestNGMethod method : context.getAllTestMethods()) {
            int providerSize = getProviderSize(method.getConstructorOrMethod().getMethod());
            boolean hasDataProvider = providerSize > 0;
            if (!hasDataProvider) {
                int invocationCount = method.getInvocationCount();
                if (invocationCount == 1) {
                    testList.add(new TestObject(method.getMethodName(), TestObject.TestResult.NOT_RUN));
                } else {
                    for (int i = 1; i < invocationCount + 1; i++) {
                        testList.add(new TestObject(method.getMethodName() + "#" + i, TestObject.TestResult.NOT_RUN));
                    }
                }
            } else {
                for (int i = 1; i < providerSize + 1; i++) {
                    testList.add(new TestObject(method.getMethodName() + "#" + i, TestObject.TestResult.NOT_RUN));
                }
            }
        }

        printTestsStatus();
    }

    private int getProviderSize(Method method) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Test testAnnotation = method.getAnnotation(Test.class);
        if (testAnnotation == null || testAnnotation.dataProvider().isEmpty()) {
            return 0;
        }
        Class<?> declaringClass = method.getDeclaringClass();
        Method dataProviderMethod = declaringClass.getMethod(testAnnotation.dataProvider());

        Object instance = null;
        if (!Modifier.isStatic(dataProviderMethod.getModifiers())) {
            instance = declaringClass.getDeclaredConstructor().newInstance();
        }
        Object[][] data = (Object[][]) dataProviderMethod.invoke(instance);

        return data.length;
    }
}
