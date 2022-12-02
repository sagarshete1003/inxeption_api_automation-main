package com.api.automation.util;

import com.api.automation.reports.ExtentTestManager;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.net.InetAddress;
import java.nio.file.Paths;
import java.util.List;

public class ReportUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReportUtil.class);

    public static String getScreenshotName() {
        String featureName = ExtentTestManager.featureFileName.get();
        if(featureName!=null) {
            Long timestamp = System.currentTimeMillis();
            return timestamp+"_"+featureName.substring(0, featureName.indexOf("."))+".png";
        } else {
            Long timestamp = System.currentTimeMillis();
            return timestamp+".png";
        }

    }

    public static void writeReportLog(boolean success, String step, String log, boolean attachScreenshot) {
        if(!success) {
            StringBuilder markup = new StringBuilder("<details><summary><b><font color=red>"
            +step+"</font></b></summary>"+log.replaceAll(",","<br>")+"</details>");
            ExtentTestManager.getTest().fail(markup.toString());
        } else {
            StringBuilder markup = new StringBuilder("<details><summary><b><font color=black>"
                    +step+"</font></b></summary>"+log.replaceAll(",","<br>")+"</details>");
            ExtentTestManager.getTest().pass(markup.toString());
        }
    }

    public static void writeReportLog(boolean success, String step, String log, String attachScreenshot) {
        if(!success) {
            StringBuilder markup = new StringBuilder("<details><summary><b><font color=red>"
                    +step+"</font></b></summary>"+log+"</details>");

            if(attachScreenshot!=null){
                //markup.append("<b><font color=red>"+"Screenshot"+"</font>");
                ExtentTestManager.getTest().fail(markup.toString(),MediaEntityBuilder.createScreenCaptureFromPath(attachScreenshot).build());
            } else {
                ExtentTestManager.getTest().fail(markup.toString());
            }
        } else {
            StringBuilder markup = new StringBuilder("<details><summary><b><font color=black>"
                    +step+"</font></b></summary>"+log+"</details>");
            if(attachScreenshot!=null){
                //markup.append("<b><font color=green>"+"Screenshot"+"</font>");
                ExtentTestManager.getTest().pass(markup.toString(),MediaEntityBuilder.createScreenCaptureFromPath(attachScreenshot).build());
            } else {
                ExtentTestManager.getTest().pass(markup.toString());
            }
        }
    }

    public static void writeReportLog(boolean success, String step, String log, List<String> attachScreenshot) {
        if(!success) {
            StringBuilder markup = new StringBuilder("<details><summary><b><font color=red>"
                    +step+"</font></b></summary>"+log.replaceAll(",","<br>")+"</details>");

            if(attachScreenshot!=null){
                ExtentTestManager.getTest().fail(markup.toString(),MediaEntityBuilder.createScreenCaptureFromPath(attachScreenshot.get(0)).build());
            } else {
                ExtentTestManager.getTest().fail(markup.toString());
            }
        } else {
            StringBuilder markup = new StringBuilder("<details><summary><b><font color=black>"
                    +step+"</font></b></summary>"+log.replaceAll(",","<br>")+"</details>");
            if(attachScreenshot!=null){
                ExtentTestManager.getTest().pass(markup.toString(),MediaEntityBuilder.createScreenCaptureFromPath(attachScreenshot.get(0)).build());
            } else {
                ExtentTestManager.getTest().pass(markup.toString());
            }
        }
    }

    public static void writeReportSkipLog(String step, String log) {
        StringBuilder markup = new StringBuilder("<details><summary><b><font color=gray>"
                +step+"</font></b></summary>"+log.replaceAll(",","<br>")+"</details>");
        ExtentTestManager.getTest().skip(markup.toString());
    }

    public static String getHostName() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostName();
        } catch(Exception ex) {
            //DO nothing
        }
        return null;
    }
}
