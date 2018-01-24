package pageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

	public class WebDriverWrapper {

		RemoteWebDriver remoteWebDriver;

		public void init() {
			ChromeOptions capabilities = new ChromeOptions();
			LoggingPreferences lp = new LoggingPreferences();
			lp.enable(LogType.BROWSER, Level.ALL);
			capabilities.setCapability(CapabilityType.LOGGING_PREFS, lp);
			remoteWebDriver = new RemoteWebDriver(capabilities);
		}

		public void init(String remoteUrl) throws MalformedURLException {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();

			remoteWebDriver = new RemoteWebDriver(new URL(remoteUrl), capabilities);

			remoteWebDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		
		public LogEntries getConsoleLogs(){
			return remoteWebDriver.manage().logs().get(LogType.BROWSER);
		}
		
		public void addCookies(String key,String val){
			Cookie cookie =  new Cookie(key,val);
			remoteWebDriver.manage().addCookie(cookie);
		}
		
		
	    public void printConsoleLogs(){
	    	List<LogEntry> consoleLogEntries = getConsoleLogs().getAll();
	    	for (LogEntry entry : consoleLogEntries){
	    		System.out.println(entry.getMessage() + " :" + entry.getTimestamp());
	    	}
	    }

		

		public void openUrl(String url) {
			remoteWebDriver.get(url);
		}

		public WebElement getElementByType(String value, String type) {
			return getElementByType(value, type, "visible");
		}

		public WebElement getElementByType(String value, String type, String condition) {

			WebElement element = null;

			By by = null;

			if (type.equals("xpath")) {
				by = By.xpath(value);
			} else if (type.equals("id")) {
				by = By.id(value);
			} else if (type.equals("class")){
				by = By.className(value);
			}

			try {
				WebDriverWait driverWait = new WebDriverWait(remoteWebDriver, 20, 1000);

				if (condition.equals("clickable")) {
					element = driverWait.until(ExpectedConditions.elementToBeClickable(by));
				} else if (condition.equals("visible")) {
					element = driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
				}

			} catch (TimeoutException e) {
				printScreen();
				System.out.println("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (element == null) {
				Assert.fail("no element of such xpath");
			}

			return element;

		}
		
		
		public WebElement get_Element(By by) {
			WebElement element = remoteWebDriver.findElement(by);

			if (element != null) {
				return element;
			} else
				return null;

		}

		public void quit() {
			remoteWebDriver.quit();
		}

		public void init2() {

			DesiredCapabilities capabilities = DesiredCapabilities.chrome();

			remoteWebDriver = new RemoteWebDriver(capabilities);
		}

		public void hoverAndClick(String xpathInitial, String xpathToClick) {
			WebElement element = getElementByType(xpathInitial, "xpath");

			Actions actions = new Actions(remoteWebDriver);

			actions.moveToElement(element).pause(Duration.ofSeconds(2))
					.moveToElement(remoteWebDriver.findElement(By.xpath(xpathToClick))).pause(Duration.ofSeconds(2)).click()
					.perform();
			;
		}

		public void dragAndDrop(String xpathFrom, String xpathTo) {
			Actions actions = new Actions(remoteWebDriver);

			WebElement elementFrom = get_Element(By.xpath(xpathFrom));

			actions.click(elementFrom).moveToElement(get_Element(By.xpath(xpathTo))).release().perform();
		}

		public void runJavascript() {

			int height = remoteWebDriver.manage().window().getSize().getHeight();
			System.out.println("current height: " + height);
			String script = "window.scrollBy(0,'" + height + "')";

			remoteWebDriver.executeScript(script);

		}
		
	

		public List<WebElement> findListOfElements(String xpath) {
			WebDriverWait driverWait = new WebDriverWait(remoteWebDriver, 20, 1000);
			List<WebElement> elementsList = null;

			try {
				elementsList = driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}

			return elementsList;

		}
		
		public void waitForElements(String xpath) {
			//should it be something of this sort?
			WebDriverWait driverWait = new WebDriverWait(remoteWebDriver, 20, 1000);
			driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		}
		
		public WebElement getStaleElement(String xpath){
			WebElement elemToReturn = null; //elem to return
			
			By xpathToWaitGet = By.xpath(xpath); //stale object xpath
			WebElement we = remoteWebDriver.findElement(xpathToWaitGet);	
			
			try {
				WebDriverWait driverWait = new WebDriverWait(remoteWebDriver,10, 1000);

				if (driverWait.until(ExpectedConditions.stalenessOf(we))){
					System.out.println("STALE ENDED?");
					elemToReturn = driverWait.until(ExpectedConditions.presenceOfElementLocated(xpathToWaitGet));
				}
				
			}  catch (Exception e) {
				e.printStackTrace();
			}

			if (elemToReturn == null) {
				Assert.fail("no element of such xpath");
			}

			return elemToReturn;
		}
		

		
		public void refreshPage(){
			remoteWebDriver.navigate().refresh();
		}
		
		
		public int getElemenetsByXpath(String xpath) {
			WebDriverWait driverWait = new WebDriverWait(remoteWebDriver, 20, 1000);

			List<WebElement> elementsList = driverWait
					.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(xpath), 1));
			

			if (elementsList != null) {
				return elementsList.size();
			} else
				return 0;
		}
		
		

		public void printScreen() {

			WebDriver augmentedDriver = new Augmenter().augment(remoteWebDriver);
			File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);

			copyFile(screenshot, System.getProperty("user.dir") + "\\files\\scr\\" + screenshot.getName());
		}

		public static void copyFile(File source, String destinationPath) {
			try {
				InputStream in = new FileInputStream(source);
				try {
					OutputStream out = new FileOutputStream(new File(destinationPath));
					try {
						// Transfer bytes from in to out
						byte[] buf = new byte[1024];
						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
					} finally {
						out.close();
					}
				} finally {
					in.close();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

}
