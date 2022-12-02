package com.api.automation.reports;

import com.aventstack.extentreports.model.Test;

import java.util.ArrayList;
import java.util.List;

public class ExecutionResult {
    public int totalTestCases;
    public int passedTestCases;
    public int failedTestCases;
    public int skippedTestCases;
    public List<Test> testList = new ArrayList<>();

    @Override
    public String toString() {
        return "ExecutionResult{" +
                "totalTestCases=" + totalTestCases +
                ", passedTestCases=" + passedTestCases +
                ", failedTestCases=" + failedTestCases +
                ", skippedTestCases=" + skippedTestCases +
                '}';
    }
}
