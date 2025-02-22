package utils;

import java.util.List;
import java.util.Map;

public class TestContext {
    private static final ThreadLocal<TestContext> testContext = new ThreadLocal<>();

    private List<Map<String, String>> testData;

    private TestContext() {
        this.testData = null;
    }

    public static TestContext getInstance() {
        if (testContext.get() == null) {
            testContext.set(new TestContext());
        }
        return testContext.get();
    }

    public static void clearInstance() {
        TestContext context = testContext.get();
        if (context != null) {
            BrowserManager.quitDriver(); // Delegate to BrowserManager
            testContext.remove();
        }
    }

    public List<Map<String, String>> getTestData() {
        return testData;
    }

    public void setTestData(List<Map<String, String>> testData) {
        this.testData = testData;
    }
}