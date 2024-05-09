package stepDefinitions.loginTest;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import actions.HomePageActions;
import actions.LoginPageActions;
import drivers.DriverManager;
import org.testng.Assert;
 
public class LoginPageDefinitions {

    private static ThreadLocal<LoginPageActions> objLogin = new ThreadLocal<>();
    private static ThreadLocal<HomePageActions> objHomePage = new ThreadLocal<>();
    
    @Before
    public void initializeVariables()
    {
    	objLogin.set(new LoginPageActions());
    	objHomePage.set(new HomePageActions());
    }
	
 
    @Given("User is on HRMLogin page {string}")
    public void loginTest(String url) {
 
        DriverManager.openPage(url);
 
    }
 
    @When("User enters username as {string} and password as {string}")
    public void goToHomePage(String userName, String passWord) {
 
        // login to application
        objLogin.get().login(userName, passWord);
        // go the next page
 
    }
 
    @Then("User should be able to login successfully and new page open")
    public void verifyLogin() {
 
        // Verify home page
        Assert.assertTrue(objHomePage.get().getHomePageText().contains("Dashboard"));
 
    }
 
    @Then("User should be able to see error message {string}")
    public void verifyErrorMessage(String expectedErrorMessage) {
 
        // Verify error message
        Assert.assertEquals(objLogin.get().getErrorMessage(),expectedErrorMessage);
 
    }
 
    @Then("User should be able to see a message {string} below Username")
    public void verifyMissingUsernameMessage(String message) {
 
        Assert.assertEquals(objLogin.get().getMissingUsernameText(),message);
    }
 
    @After
    public void tearDownVariables(){
    	objLogin.remove();
    	objHomePage.remove();
    }
}