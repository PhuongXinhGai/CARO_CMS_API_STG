package demo;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class TestListener implements ITestListener {

    private static ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        ExtentReportManager.getInstance();
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = ExtentReportManager.getInstance().createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test Failed: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.getInstance().flush();
    }
}
