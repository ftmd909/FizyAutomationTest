package Utils.listeners;

import Pages.BasePage;
import Utils.reports.ExtentManager;
import Utils.reports.ExtentTestManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestListener implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    private static ExtentReports extent = ExtentManager.getInstance();

    @Override
    public void onTestStart(ITestResult result) {
        String className = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();
        ExtentTestManager.createTest(className + " - " + methodName, result.getMethod().getDescription());
        ExtentTestManager.getTest().info(result.getMethod().getMethodName() + " Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        ExtentTestManager.getTest().log(Status.PASS, methodName + " Finished");
        ExtentTestManager.getTest().log(Status.PASS, "Test case Passed");




    }

    @Override
    public void onTestFailure(ITestResult result) {


        try {

            ExtentTestManager.getTest().log(Status.FAIL, result.getThrowable().getMessage());

            String ssSource = captureScreenshot(BasePage.drivers());
            String thumbnailPath = "data:image/gif;base64," + ssSource;

            ExtentTestManager.getTest().fail("<span class='label end-time'>Screenshot of the error</span><a href='" + thumbnailPath + "' data-featherlight='image'> <img style='width:150px;height:100px;display:block;margin-top:5px' src='" + thumbnailPath + "'> </a>");

            Reporter.setCurrentTestResult(result);
            Reporter.log("<a href=''> <img width='300' height='300' src='" + thumbnailPath + "'> </a>");
            Reporter.setCurrentTestResult(null);


        } catch (NullPointerException e) {
        }
        BasePage.drivers().close();




    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().skip("<span class='label end-time'>Test skipped</span>");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    public String captureScreenshot(WebDriver driver) {
        try {
            String ssSource = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            //String ssBase64="data:image/png;base64,"+ssSource;
            return ssSource;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }

    }

}
