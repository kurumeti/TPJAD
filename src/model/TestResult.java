package model;

public class TestResult {
    private String testName;
    private String testCase;
    private String testResult;

    public TestResult(String testName, String testCase, String testResult) {
        this.testName = testName;
        this.testCase = testCase;
        this.testResult = testResult;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestCase() {
        return testCase;
    }

    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }
}
