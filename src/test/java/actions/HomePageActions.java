package actions;

import locators.HomePageLocators;
import drivers.DriverManager;
import org.openqa.selenium.support.PageFactory;
 
public class HomePageActions {
 
    HomePageLocators homePageLocators = null;
 
    public HomePageActions() {
 
        this.homePageLocators = new HomePageLocators();
        PageFactory.initElements(DriverManager.getDriver(),homePageLocators);
    }
 
    // Get the User name from Home Page
    public String getHomePageText() {
        return homePageLocators.homePageUserName.getText();
    }
 
}