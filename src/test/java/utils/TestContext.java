package utils;

import java.util.List;
import java.util.Map;

public class TestContext {
    private List<Map<String, String>> testData;
    private String featureFileName;

    public TestContext() {
        this.testData = null;
        this.featureFileName = null;
    }

    public List<Map<String, String>> getTestData() {
        return testData;
    }

    public void setTestData(List<Map<String, String>> testData) {
        this.testData = testData;
    }

    public String getFeatureFileName() {
        return featureFileName;
    }

    public void setFeatureFileName(String featureFileName) {
        this.featureFileName = featureFileName;
    }
}