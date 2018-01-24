package tests;

import org.junit.After;
import org.junit.Before;

import pageObjects.GenericPageObject;
import pageObjects.WebDriverWrapper;

public class BasicTest {

	WebDriverWrapper driverWrapper;
	
	@Before
	public void before(){
		//init the wrapper
		driverWrapper = new WebDriverWrapper();
		driverWrapper.init();
		
		//inject the driverWrapper into generic class
		GenericPageObject.setWebDriver(driverWrapper);
		
	}
	
	@After
	public void after() throws InterruptedException{
		//quit the stuff
		driverWrapper.printConsoleLogs();
		Thread.sleep(3000);
		driverWrapper.quit();
	}

}

