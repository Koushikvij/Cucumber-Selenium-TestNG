package runner;
 
import io.cucumber.testng.CucumberOptions;
import utilities.Log;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import java.io.File;
import org.apache.commons.text.WordUtils;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import drivers.DriverManager;
    
@CucumberOptions(
		tags = "", 
		features = {"src/test/resources/features"}, 
		glue = {"stepDefinitions"},
		plugin= {"pretty","html:target/cucumber.html",
         "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
         "rerun:target/failedrerun.txt"
        },
		monochrome=true,
		publish = true)    
public class testRunner extends AbstractTestNGCucumberTests {
	
	@BeforeSuite()
	public void clearLogFile()
	{
		String logFilePath = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "Logs" + File.separator+ "test_automation.log";
		File f=new File(logFilePath);
        if(f.exists())
        	f.delete();
        
        String log4jConfPath = System.getProperty("user.dir") + File.separator + "src" + File.separator+ "test" + File.separator + "resources"
                + File.separator + "config" + File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);		
	}

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser", "mode", "environment", "client", "implicitwaitduration", "timeunit", "os", "url"})
    public void initSession(String pbrowser, String pmode, String penvironment, String pclient, long pimplicitwaitduration, String ptimeunit, String pos, String purl)
    {
        try
        {
            Log.info("Current System OS -> " + System.getProperty("os.name"));
            DriverManager.browser = WordUtils.capitalize(pbrowser);
            Log.info("Browser=" + DriverManager.browser);
            DriverManager.testOS = System.getProperty("os.name");
            Log.info("OS=" + DriverManager.testOS);
            DriverManager.mode = pmode;
            Log.info("Mode=" + DriverManager.mode);
            DriverManager.environment = penvironment;
            Log.info("Environment=" + DriverManager.environment);
            DriverManager.client = pclient;
            Log.info("Client=" + DriverManager.client);
            DriverManager.buildNumber = System.getProperty("jenkinsbuildnum");
            Log.info("buildNumber=" + DriverManager.buildNumber);
            DriverManager.projectName = System.getProperty("jenkinsjobname");
            Log.info("projectName=" + DriverManager.projectName);
            DriverManager.implicitwaitduration = pimplicitwaitduration;
            DriverManager.timeunit = ptimeunit;
            Log.info(System.getProperty("user.dir"));
            DriverManager.baseurl = purl;
        }
        catch(Exception e)
        {
        	Assert.assertTrue(false,"Exception found in Method - initSession : " + e.getMessage());
        }
    }
    
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}