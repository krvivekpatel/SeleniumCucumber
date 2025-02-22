package context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private static ThreadLocal<TestContext> contextThreadLocal = new ThreadLocal<>();

    private Map<String, Object> scenarioContext = new HashMap<>();
    private String featureFileName;
    private String scenarioName;
    private int stepIndex;

    // Make the constructor public
    public TestContext() {}

    public static TestContext get() {
        if (contextThreadLocal.get() == null) {
            contextThreadLocal.set(new TestContext());
        }
        return contextThreadLocal.get();
    }

    public void setScenarioData(String key, Object value) {
        scenarioContext.put(key, value);
    }

    public Object getScenarioData(String key) {
        return scenarioContext.get(key);
    }

    public String getFeatureFileName() {
        return featureFileName;
    }

    public void setFeatureFileName(String featureFileName) {
        this.featureFileName = featureFileName;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public int getStepIndex() {
        return stepIndex;
    }

    public void incrementStepIndex() {
        this.stepIndex++;
    }

    public static void cleanup() {
        contextThreadLocal.remove();
    }
}
