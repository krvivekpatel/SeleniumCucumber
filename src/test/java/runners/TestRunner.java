package runners;

import io.cucumber.picocontainer.PicoFactory;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefinition",  // Package containing step definitions
        plugin = {"pretty", "html:target/cucumber-reports/cucumber.html"},
        tags = "@Test", // You can specify tags to run specific tests
        monochrome = true, // To make the console output more readable
        objectFactory = PicoFactory.class
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
