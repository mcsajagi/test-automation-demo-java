package Reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reports {
    private static ExtentReports extentReports;

    public static ExtentReports createInstance(String testType) {
        if (extentReports == null) {
            String timestamp = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss").format(new Date());
            String baseDir = "target";
            String reportFolder;

            if ("api".equalsIgnoreCase(testType)) {
                reportFolder = "apiReports";
            } else if ("selenium".equalsIgnoreCase(testType)) {
                reportFolder = "seleniumReports";
            } else {
                reportFolder = "reports";
            }

            File dir = new File(baseDir + File.separator + reportFolder);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String reportPath = dir.getAbsolutePath() + File.separator + "report_" + timestamp + ".html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);

            String runMode = System.getProperty("runMode");
            if (runMode != null && runMode.equals("CI")) {
                extentReports.setSystemInfo("Execution Mode", "CI");
            } else {
                extentReports.setSystemInfo("Execution Mode", "Local");
            }
        }
        return extentReports;
    }
}