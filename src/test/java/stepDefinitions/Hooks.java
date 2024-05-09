package stepDefinitions;

import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import drivers.DriverManager;
import io.cucumber.java.*;

public class Hooks {
 
    @Before
    public static void setUp() throws IOException { 
       DriverManager.setUpDriver();
    }
	
    @After
    public static void tearDown(Scenario scenario) {
 
        //validate if scenario has failed
        if(scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName()); 
        }   
         
        DriverManager.tearDown();
    }
    
    @AfterStep
    public void AddScreenshot(Scenario scenario) throws IOException
    {
        final byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", scenario.getName()); 
    }
}