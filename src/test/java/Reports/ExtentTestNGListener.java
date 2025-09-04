package Reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestNGListener implements ITestListener {

    private ExtentReports extentReports;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        String testType = System.getProperty("testType");
        if (testType == null || testType.isEmpty()) {
            testType = "mixed";
        }
        extentReports = Reports.createInstance(testType);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        String testClassName = result.getTestClass().getName();

        String prefix;
        if (testClassName.contains("apiTests")) {
            prefix = "[API] ";
        } else if (testClassName.contains("seleniumTests")) {
            prefix = "[UI] ";
        } else {
            prefix = "[MIXED] ";
        }

        if (description == null || description.trim().isEmpty()) {
            description = "No description provided.";
        }

        ExtentTest test = extentReports.createTest(prefix + methodName, description);
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.getThrowable() != null) {
            extentTest.get().skip(result.getThrowable());
        } else {
            extentTest.get().skip("Test skipped without exception.");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }
}