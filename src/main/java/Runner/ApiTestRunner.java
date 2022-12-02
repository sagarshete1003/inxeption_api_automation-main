package Runner;

import com.api.automation.constants.Constants;
import com.api.automation.exception.AutomationException;
import com.api.automation.reports.ExtentManager;
import com.api.automation.reports.ExecutionResult;
import com.api.automation.reports.ExtentTestManager;
import com.api.automation.util.*;
import com.aventstack.extentreports.Status;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.TestNGCucumberRunnerCustom;
import cucumber.runtime.model.CucumberFeature;
import gherkin.events.PickleEvent;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.*;
import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@CucumberOptions(
        features = {"features/easy-order-api.feature"},
        glue = { "com.api.automation.hooks","glue"},
        monochrome = true)
public class ApiTestRunner implements ITest {
    ReportLogger reportLogger = ReportLogger.getInstance();
    public static ThreadLocal<TestNGCucumberRunnerCustom> RUNNER = new ThreadLocal<>();
    public static ThreadLocal<PickleEvent> CURRENT_SCENARIO_EXECUTION = new ThreadLocal<>();
    private final ThreadLocal<String> testName = new ThreadLocal<>();
    protected TestNGCucumberRunnerCustom testNGCucumberRunner;
    private static int PASSED_FEATURES = 0;

    @BeforeClass(alwaysRun = true)
    public void init(ITestContext iTestContext) throws IOException {
        reportLogger.log("----------------------- API Test Automation -----------------------");
        testNGCucumberRunner = new TestNGCucumberRunnerCustom(this.getClass());
    }

    @Test(priority = 1, description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeature feature) throws AutomationException {
        try {
            String uri = feature.getUri();
            uri = uri.substring(uri.lastIndexOf("/")+1);
            TestNGCucumberRunnerCustom testNGCucumberRunner = new TestNGCucumberRunnerCustom(this.getClass());
            RUNNER.set(testNGCucumberRunner);
            ExtentTestManager.setFeatureFileName(uri);
            reportLogger.log("Test Feature: "+uri);
            List<PickleEvent> pickles = testNGCucumberRunner.runtime.compileFeature(feature);
            for(PickleEvent pickle: pickles) {
                try {
                    CURRENT_SCENARIO_EXECUTION.set(pickle);
                    testNGCucumberRunner.runScenario(pickle);
                } catch(Throwable rx) {
                    rx.printStackTrace();
                }
            }
            testNGCucumberRunner.finish();
        } catch(Throwable ex) {
            throw new AutomationException(ex.getMessage());
        } finally {
            PASSED_FEATURES++;
        }
    }

    @Test(dependsOnMethods = {"feature"}, priority = 2, dataProvider = "testResults")
    public void testAnalysis(com.aventstack.extentreports.model.Test test) {
        if(test.getStatus().equals(Status.FAIL)) {
            throw new RuntimeException(test.getName());
        } else if(test.getStatus().equals(Status.SKIP)) {
            throw new SkipException(test.getName());
        }
    }

    @DataProvider(name="features", parallel=true)
    public Object[][] features() {
        List<CucumberFeature> features = testNGCucumberRunner.getFeatures();
        List<Object[]> featuresList = new ArrayList<>(features.size());
        Iterator<CucumberFeature> var3 = features.iterator();

        while(var3.hasNext()) {
            CucumberFeature feature = (CucumberFeature)var3.next();
            featuresList.add(new Object[]{feature});
        }
        return (Object[][])featuresList.toArray(new Object[0][]);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        testNGCucumberRunner.finish();
        ExtentManager.getExtentReports().flush();
        reportLogger.log("Test Execution Completed!");
    }

    @DataProvider(name="testResults", parallel=true)
    public Object[][] testResultDataProvider() {
        ExecutionResult result = ExtentTestManager.getResult();
        List<com.aventstack.extentreports.model.Test> results = result.testList;
        List<Object[]> testList = new ArrayList<>(results.size());
        int passedScenarioCount = result.passedTestCases - PASSED_FEATURES;
        int failedScenarioCount = result.failedTestCases;
        int totalTestToKeep = passedScenarioCount + failedScenarioCount;
        int index = 0;
        while(testList.size()<passedScenarioCount) {
            com.aventstack.extentreports.model.Test test = results.get(index);
            if(!test.getName().contains("Launch Browser") && test.getStatus().equals(Status.PASS)) {
                testList.add(new Object[]{test});
            }
            index++;
            if(index>results.size()-1)
                break;
        }
        index = 0;
        while(testList.size()<passedScenarioCount) {
            com.aventstack.extentreports.model.Test test = results.get(index);
            if(test.getName().contains("Launch Browser") && test.getStatus().equals(Status.PASS)) {
                testList.add(new Object[]{test});
            }
            index++;
            if(index>results.size()-1)
                break;
        }
        index = 0;
        while(testList.size()<totalTestToKeep) {
            com.aventstack.extentreports.model.Test test = results.get(index);
            if(test.getStatus().equals(Status.FAIL)) {
                testList.add(new Object[]{test});
            }
            index++;
            if(index>results.size()-1)
                break;
        }
        for(com.aventstack.extentreports.model.Test test: results) {
            if(test.getStatus().equals(Status.SKIP)) {
                testList.add(new Object[]{test});
            }
        }
        return (Object[][])testList.toArray(new Object[0][]);
    }

    @BeforeMethod
    public void BeforeMethod(Method method, Object[] testData){
        Object data = testData[0];
        if(data instanceof com.aventstack.extentreports.model.Test) {
            testName.set(((com.aventstack.extentreports.model.Test) data).getName());
        } else if(data instanceof CucumberFeature) {
            String uri = ((CucumberFeature) data).getUri();
            uri = uri.substring(uri.lastIndexOf("/")+1);
            testName.set(uri);
        }
    }

    @Override
    public String getTestName() {
        return testName.get();
    }
}