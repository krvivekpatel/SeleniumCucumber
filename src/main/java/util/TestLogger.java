package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

public class TestLogger {

    private static ThreadLocal<Logger> threadLocalLogger = ThreadLocal.withInitial(() -> LogManager.getLogger("TestLogger"));

    // Method to set the logger for the current thread (Scenario)
    public static void setScenarioLogger(String scenarioName){
    ThreadContext.put("scenarioName", scenarioName);
        threadLocalLogger.set(LogManager.getLogger(scenarioName));
    }

    // Get the logger for the current thread (Scenario)
    public static Logger getLogger() {
        return threadLocalLogger.get();
    }

    // Clear the logger after the scenario ends
    public static void clearLogger() {
        threadLocalLogger.remove();
    }
}
