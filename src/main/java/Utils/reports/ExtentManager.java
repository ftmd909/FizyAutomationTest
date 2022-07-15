package Utils.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExtentManager {
    public static String reportName;
    private static ExtentReports extent;

    public static ExtentReports createInstance() {
        reportName = new SimpleDateFormat("ddMMyyyy_HHmm").format(Calendar.getInstance().getTime());
        reportName = "test-output/"+reportName + "_AutomationReport.html";
        String extentCss = ".report-name {font-size:0px} .report-name::after {content: 'Fizy Test Automation';font-size:14px !important} ";
        extentCss += ".nav-wrapper .brand-logo {font-size:0px} .nav-wrapper .brand-logo::after {content: 'Fizy';font-size:12px !important} ";
        extentCss += "#nav-mobile li:nth-child(2){display:none} ";

        try {
            Files.createDirectories(Paths.get("test-output"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportName);
        htmlReporter.config().setCSS(extentCss);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        extent.setSystemInfo("FizyTest", "https://fizy.com/");

        extent.setSystemInfo("Browser", "Chrome");
        return extent;
    }
    public static ExtentReports getInstance() {
        if(extent == null) {
            createInstance();
        }
        return extent;
    }
}
