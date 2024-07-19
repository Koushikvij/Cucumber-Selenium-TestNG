package utilities;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import drivers.DriverManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils
{
    public static final long WAIT = 7;
    public static Logger Log = LoggerFactory.getLogger(utilities.Log.class);


    public static synchronized boolean compareInput(WebElement element, String str)
    {
        try
        {
            String strVar = element.getAttribute("value");
            Log.info("Value attribute for given WebLElement -> " + strVar);
            if(strVar.equalsIgnoreCase(str.trim()) || str.trim().contains(strVar))
            {
                return true;
            }
        }
        catch(Exception e)
        {
        	Assert.assertTrue(false,"Text does not match to <b>" + str + "</b>" + " " + "Actual text is : " + element.getText());
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static synchronized boolean compareText(WebElement element, String str)
    {
        try
        {
            String strVar = element.getText();
            Assert.assertEquals(strVar,str);
            Log.info("WebElement has same text as :" + str);
            return true;
        }
        catch(AssertionError e)
        {
        	Assert.assertTrue(false,"Text does not match to <b>" + str + "</b>" + " " + "Actual text is : " + element.getText());
            e.printStackTrace();
            return false;
        }
        catch(NoSuchElementException ex)
        {
        	Assert.assertTrue(false,"Element does not exist");
            ex.printStackTrace();
            return false;
        }
    }

    public static synchronized boolean IsErrorMsgVisible(WebElement element)
    {
        if(element.isDisplayed()) return true;

        return false;
    }

    public static synchronized boolean IsErrorMsgHidden(WebElement element)
    {
        boolean flag = true;
        try
        {
            if(element.isDisplayed())
                flag = false;
        }
        catch(NoSuchElementException exception)
        {
            Log.error(ExceptionUtils.getStackTrace(exception));
        }
        return flag;
    }


    public static synchronized String getAttribute(WebDriver driver, WebElement e, String attribute)
    {
        waitForVisibility(driver, e);
        Log.info(attribute + " value is --> " + e.getAttribute(attribute));
        return e.getAttribute(attribute);
    }

    public static synchronized void waitForVisibility(WebDriver driver, WebElement e)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT));
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public static synchronized String getText(WebDriver driver, WebElement e)
    {
        String txt;
        txt = getAttribute(driver, e, "text");
        Log.info("Got Text as : " + txt);
        return txt;
    }

    public static synchronized boolean CheckElementExists(WebElement element)
    {
        try
        {
            By byval =Utilities.toByVal(element);
            if(DriverManager.getDriver().findElements(byval).size() > 0)
            {
                Log.info("Element exists in DOM-> " + element);
                return true;
            }
            else
            {
                Log.info("Element doesn't exist in DOM -> " + element);
                return false;
            }
        }
        catch(NoSuchElementException e)
        {
           Log.error(ExceptionUtils.getStackTrace(e));
       		Assert.assertTrue(false,"Verify " +  element + " exists in DOM");
        }
        return false;
    }

    /**
     * Function to switch to window
     *
     * @param noOfWindows
     */
    public static synchronized void switchToWindow(WebDriver driver, int noOfWindows)
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.numberOfWindowsToBe(noOfWindows));
            for(String winHandle : driver.getWindowHandles())
            {
//				win.add(winHandle);
                driver.switchTo().window(winHandle);
            }
        }
        catch(Exception e)
        {
            Log.error("No window is displayed!");
        }
    }

    /**
     * Function to switch the tab
     *
     * @param tab
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static synchronized void switchTab(WebDriver driver, int tab)
    {
        ArrayList<String> window = new ArrayList(driver.getWindowHandles());
        driver.switchTo().window(window.get(tab));
    }

    public static synchronized void switchToParentFrame(WebDriver driver)
    {
        driver.switchTo().defaultContent();
    }


    public static synchronized boolean IsElementDisplayed(WebElement element)
    {
        try
        {
            if(element.isDisplayed())
            {
                Log.info("Element is Visible -> " + element);
                return true;
            }
        }
        catch(Exception e)
        {
            Log.info("Element is not Visible -> " + element);
            return false;
        }
        return false;
    }

    public static synchronized boolean IsElementEnabled(WebElement element)
    {
        try
        {
            if(element.isEnabled())
            {
                Log.info("Element is enabled -> " + element);
                return true;
            }
        }
        catch(Exception e)
        {
            return false;
        }
        return false;
    }

    /**
     * Check if the given WebElement is Selected
     * @param element WebElement
     * @return boolean
     */
    public static synchronized boolean IsElementSelected(WebElement element)
    {
        try
        {
            if(element.isSelected())
            {
                Log.info("Element is selected -> " + element);
                return true;
            }
            else
            {
                Log.info("Element is not selected -> " + element);
                return false;
            }
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
     * boolean return type for conditions
     *
     * @return
     * @throws Exception
     */
    public static synchronized boolean IsElementDisplayedAndEnabled(WebElement element)
    {
        try
        {
            if(element.isDisplayed() && element.isEnabled())
            {
                return true;
            }
            if(element.isDisplayed() && !element.isEnabled())
            {
                return false;
            }
            if(!element.isDisplayed() && !element.isEnabled())
            {
                return false;
            }
            if(!element.isDisplayed() && element.isEnabled())
            {
                return false;
            }
        }
        catch(Exception e)
        {
            return false;
        }
        return false;
    }


    public static void switchToIFrame(WebDriver driver, WebElement element)
    {
        driver.switchTo().frame(element);
    }

    /**
     *
     * @param PageName
     * @param PagePattern
     * @return
     */
    public static synchronized boolean VerifyPageNavigation(String PageName, String PagePattern)
    {
        try
        {
            String pattern = "https:\\/\\/" +  DriverManager.environment + "\\." + DriverManager.client + "\\.com\\/management\\/[a-zA-Z0-9]\\w+/" + PagePattern ;
            String Url = DriverManager.getDriver().getCurrentUrl();
            Matcher matcher = Pattern.compile(pattern,Pattern.MULTILINE).matcher(Url);

            if (matcher.find())
            {
            	Assert.assertTrue(true,"Verify URL contains <b>" + PagePattern + "</b> after navigating to " + WordUtils.capitalize(PageName) + " " + "Url contains " + PagePattern + " as : " + Url);
                return true;
            }
            else
            	Assert.assertTrue(true,"Verify URL contains <b>" + PagePattern + "</b> after navigating to " + WordUtils.capitalize(PageName) + " " + "Url contains " + PagePattern + " as : " + Url);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @MethodName : verifyLink
     * @Functionality: verify if a url/link is reachable or broken
     * @param: FileUrl
     * @return : True -> Link is Broken, False -> Link Works
     */
    public static boolean VerifyLinkIsBroken(String linkUrl)
    {
        Boolean broken = true;
        try
        {
            URL url = new URL(linkUrl);
            HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
            httpURLConnect.setConnectTimeout(5000);
            httpURLConnect.connect();
            if(httpURLConnect.getResponseCode() >= 400)
            {
                Log.info("HTTP STATUS - " + httpURLConnect.getResponseMessage() + "is a broken link");
            }
            else
            {
                Log.info("HTTP STATUS - " + httpURLConnect.getResponseMessage());
                broken = false;
            }
        }
        catch (Exception e)
        {
        	Assert.assertTrue(true,"Verify if the link is broken" + " " +"Link is broken : " + linkUrl);
            e.printStackTrace();
        }
        return broken;
    }
}