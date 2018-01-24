package pageObjects;

public class GenericPageObject {

	public static WebDriverWrapper driverWrapper;
	
	public static void setWebDriver(WebDriverWrapper _driverWrapper) {
		driverWrapper = _driverWrapper;
	}
}
