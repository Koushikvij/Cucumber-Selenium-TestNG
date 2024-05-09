package actions;

import locators.LoginPageLocators;
import drivers.DriverManager;
import org.openqa.selenium.support.PageFactory;
 
public class LoginPageActions {
 
    LoginPageLocators loginPageLocators = null;
 
    public LoginPageActions() {
 
        this.loginPageLocators = new LoginPageLocators();
 
        PageFactory.initElements(DriverManager.getDriver(),loginPageLocators);
    }
 
    // Get the error message when username is blank
    public String getMissingUsernameText() {
        return loginPageLocators.missingUsernameErrorMessage.getText();
    }
 
    // Get the Error Message
    public String getErrorMessage() {
        return loginPageLocators.errorMessage.getText();
    }
 
    public void login(String strUserName, String strPassword) {
 
        // Fill user name
        loginPageLocators.userName.sendKeys(strUserName);
 
        // Fill password
        loginPageLocators.password.sendKeys(strPassword);
 
        // Click Login button
        loginPageLocators.login.click();
 
    }
}