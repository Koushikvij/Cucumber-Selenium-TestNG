package drivers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.HashMap;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.Log;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class DriverManager {
	public static ThreadLocal<DriverManager> helperClass = new ThreadLocal<>();
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public final static int TIMEOUT = 5;
	//public static String screenshotBasePath = System.getProperty("user.dir") + File.separator + "screenshots";
	public static String downloadFilepath = System.getProperty("user.dir") + File.separator + "DownloadPath";
	//public static String folderPath;
	public static File outputFile;
    public static String environment = "";
    public static String client = "";
    public static String browser = "";
    public static String version = "";
    public static String mode = "";
    public static String testOS = "";
    public static String buildNumber = "";
    public static String projectName = "";
    public static String baseurl = "";
    public static long implicitwaitduration = 0;
    public static String timeunit = "";


	private DriverManager() {
		try {
	        Log.info("Initializing " + browser + " Driver"); 
			WebDriver webdriver;
	        switch(browser.toLowerCase())
	        {
	            case "safari":
	            	webdriver = createSafariDriver();
	    			setDriver(webdriver);
	                break;
	            case "firefox":
	            	webdriver = createFirefoxDriver();
	    			setDriver(webdriver);
	                break;
	            case "edge":
	            	webdriver = createEdgeDriver();
	    			setDriver(webdriver);
	                break;
	            case "chrome":	
	            	webdriver = createChromeDriver();
	    			setDriver(webdriver);
	                break;
	        }
			getDriver().manage().window().maximize();
			getDriver().manage().deleteAllCookies();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
		} catch (Exception e) {
		}
	}
	
	public static WebDriver createSafariDriver()
	{
		WebDriver webdriver;
        WebDriverManager.safaridriver().setup();
        SafariOptions options = new SafariOptions();
        webdriver = new SafariDriver(options);
        version = ((SafariDriver)webdriver).getCapabilities().getBrowserVersion();
        return webdriver;
	}
	
	public static WebDriver createFirefoxDriver()
	{
		WebDriver webdriver;
        WebDriverManager.firefoxdriver().setup();
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("pdfjs.disabled", true);
		profile.setPreference("browser.download.dir", downloadFilepath);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.showWhenStarting",false);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");

        if(mode.equalsIgnoreCase("headless"))
        {
            firefoxOptions.addArguments("-headless");
        }
        else
        {
            firefoxOptions.setCapability("acceptInsecureCerts", true);
            firefoxOptions.setProfile(profile);
        }
        webdriver = new FirefoxDriver(firefoxOptions);
        version = ((FirefoxDriver)webdriver).getCapabilities().getBrowserVersion();		
        return webdriver;
	}
	
	public static WebDriver createEdgeDriver()
	{
		WebDriver webdriver;
        WebDriverManager.edgedriver().setup();
        EdgeOptions edgeOptions = new EdgeOptions();
		HashMap<String, Object> edgePrefs= new HashMap<>();
        edgePrefs.put("profile.default_content_settings.popups", 0);
        edgePrefs.put("profile.default_content_setting_values.notifications", 2);
        edgePrefs.put("profile.default_content_setting_values.automatic_downloads" , 1);
        edgePrefs.put("profile.content_settings.pattern_pairs.*,*.multiple-automatic-downloads",1);
        edgePrefs.put("plugins.always_open_pdf_externally", true);
        edgePrefs.put("download.directory_upgrade", true);
        edgePrefs.put("download.prompt_for_download", false);
		edgePrefs.put("download.default_directory", downloadFilepath);

        edgeOptions.addArguments("InPrivate");
        edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        edgeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        edgeOptions.addArguments("--disable-notifications");
        edgeOptions.addArguments("--remote-allow-origins=*");
        edgeOptions.setExperimentalOption("prefs", edgePrefs);

		webdriver = new EdgeDriver(edgeOptions);
        version = ((EdgeDriver)webdriver).getCapabilities().getBrowserVersion();
        return webdriver;
	}
	public static WebDriver createChromeDriver()
	{
		WebDriver webdriver;
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        chromeOptions.addArguments("--disable-features=DownloadBubble,DownloadBubbleV2");

        if(mode.equalsIgnoreCase("headless"))
        {
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--verbose");
            chromeOptions.addArguments("--disable-notifications");
            chromeOptions.setProxy(null);
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            chromeOptions.addArguments("--remote-allow-origins=*");
        }
        else
        {
            chromeOptions.addArguments("disable-features=DownloadBubble,DownloadBubbleV2");
            chromeOptions.addArguments("--disable-notifications");
            chromeOptions.addArguments("--ignore-certificate-errors");
            chromeOptions.addArguments("--start-maximized");
            chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            chromeOptions.addArguments("--remote-allow-origins=*");
        }
        webdriver = new ChromeDriver(chromeOptions);
        version = ((ChromeDriver)webdriver).getCapabilities().getBrowserVersion();
        return webdriver;
	}

	public static void openPage(String url) {
		try {
			getDriver().get(url);
		} catch (Exception e) {
		}
	}

	public static void setDriver(WebDriver webdriver) {
		try {
			driver.set(webdriver);
		} catch (Exception e) {
		}
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void setUpDriver() {
		try {
			if (helperClass.get() == null) {
				helperClass.set(new DriverManager());
			}
		} catch (Exception e) {
		}
	}

//    public static String screenshot_TimeStamp_Folder()
//    {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
//        outputFile = new File(screenshotBasePath + File.separator + "screenshot_" + timeStamp);
//        outputFile.mkdir();
//        return screenshotBasePath + File.separator + "screenshot_" + timeStamp;
//    }

	public static void tearDown() {
		try {
			if (driver != null) {
				getDriver().close();
				getDriver().quit();
			}
			helperClass.remove();
		} catch (Exception e) {
		}
	}
}