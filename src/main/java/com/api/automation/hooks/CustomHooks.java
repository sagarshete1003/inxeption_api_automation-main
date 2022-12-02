package com.api.automation.hooks;

import com.api.automation.MainApplication;
import Runner.ApiTestRunner;
import com.api.automation.constants.Constants;
import com.api.automation.exception.AutomationException;
import com.api.automation.util.*;
import com.api.automation.models.TestScenario;
import com.api.automation.reports.ExtentTestManager;
import com.aventstack.extentreports.ExtentTest;
import cucumber.api.PendingException;
import cucumber.api.Result;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import cucumber.api.java.BeforeStep;
import cucumber.runtime.ScenarioImpl;
import gherkin.events.PickleEvent;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.log4j.PropertyConfigurator;
import org.testng.SkipException;
import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class CustomHooks {
    public static ReportLogger reportLogger = ReportLogger.getInstance();
    public static ThreadLocal<Scenario> CURRENT_SCENARIO = new ThreadLocal<>();
    public static ThreadLocal<String> CURRENT_SCENARIO_MESSAGE = new ThreadLocal<>();
    public static ThreadLocal<String> CURRENT_STEP_MESSAGE = new ThreadLocal<>();
    public static ThreadLocal<Integer> currentStepIndex = new ThreadLocal<>();
    public static ThreadLocal<String> currentStep = new ThreadLocal<>();
    public static ThreadLocal<String> stepScreenshot = new ThreadLocal<>();
    public static ThreadLocal<List<String>> stepScreenshots = new ThreadLocal<>();

    public static void logError(String error) {
        reportLogger.log(ExtentTestManager.featureFileName.get()+" => " + error);
        CURRENT_SCENARIO_MESSAGE.set(error);
    }

    public static void logError(String error, List<String> screenshot) {
        stepScreenshots.set(screenshot);
        reportLogger.log(ExtentTestManager.featureFileName.get()+" => " + error);
        CURRENT_SCENARIO_MESSAGE.set(error);
    }

    public static void logInfo(String... data) {
        currentStep.set(data[0]);
    }

    static {
        cleanUP();
        setup();
    }

    public static void cleanUP(){
        File logs = new File("./logs");
        if(logs.exists())
            PackageUtil.recursiveDelete(logs);
        File extentReports = new File("./extent-reports");
        if(extentReports.exists())
            PackageUtil.recursiveDelete(extentReports);
    }

    public static void setup() {
        try {
            MainApplication.init();
            String logPropertyFileName = Constants.PROPERTY_FILE_PATH + "config/log4j.properties";
            PropertyConfigurator.configure(logPropertyFileName);
            PropertyReader.loadProperties(PropertyReader.CONFIG_PROPERTIES_FILE);
            PropertyReader.loadEnvJsonProperties(PropertyReader.ENV_JSON_FILE);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Before
    public void beforeScenario(Scenario scenario) throws AutomationException {
        reportLogger.log(ExtentTestManager.featureFileName.get()+" => " + scenario.getName());
        reportLogger.log("----------------------- TEST STARTED -----------------------");
        currentStepIndex.set(0);
        CURRENT_SCENARIO.set(scenario);
        CURRENT_SCENARIO_MESSAGE.set(null);
        CURRENT_STEP_MESSAGE.set(null);
        ExtentTest test = ExtentTestManager.startTest(scenario.getName(), scenario.getName());
        ExtentTestManager.assignCategory(test);
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) {
        int index = currentStepIndex.get();
        PickleEvent pickleEvent = ApiTestRunner.CURRENT_SCENARIO_EXECUTION.get();
        if(pickleEvent!=null) {
            String stepName = pickleEvent.pickle.getSteps().get(index).getText();
            currentStep.set(stepName);
        }
        stepScreenshot.set(null);
        CURRENT_STEP_MESSAGE.set(null);
        currentStepIndex.set(index+1);

    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        try {
            String stepName = currentStep.get();
            if(stepName==null)
                return;
            reportLogger.log(ExtentTestManager.featureFileName.get()+" => " + currentStep.get());
            if(scenario.isFailed())
                ReportUtil.writeReportLog(false,stepName,CURRENT_SCENARIO_MESSAGE.get()!=null?CURRENT_SCENARIO_MESSAGE.get():stepName,stepScreenshot.get());
            else
                ReportUtil.writeReportLog(true,stepName,CURRENT_STEP_MESSAGE.get()!=null?CURRENT_STEP_MESSAGE.get():stepName,stepScreenshot.get());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            currentStepIndex.set(0);
            if(scenario.getStatus().equals(Result.Type.FAILED)) {
                logErrorInReport(scenario);
                reportLogger.log(ExtentTestManager.featureFileName.get()+" => " + scenario.getName());
                reportLogger.log("---------------------- TEST COMPLETED ----------------------");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            CURRENT_SCENARIO_MESSAGE.set(null);
            CURRENT_STEP_MESSAGE.set(null);
        }
    }

    private static void logErrorInReport(Scenario scenario) {
        Field field = FieldUtils.getField(((ScenarioImpl) scenario).getClass(), "stepResults", true);
        field.setAccessible(true);
        try {
            ArrayList<Result> results = (ArrayList<Result>) field.get(scenario);
            for (Result result : results) {
                if (result.getError() != null)
                    ReportUtil.writeReportLog(false,currentStep.get(),CURRENT_SCENARIO_MESSAGE.get()!=null?(CURRENT_SCENARIO_MESSAGE.get()+", "):""+result.getError(),true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ReportUtil.writeReportLog(false,currentStep.get(),e.getMessage()+", Error while logging error: "+ Arrays.toString(e.getStackTrace()),true);
        }
    }
}
