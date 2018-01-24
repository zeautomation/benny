package pageObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebElement;

public class PageObjects {

	public static class VroomHomePage extends GenericPageObject{
		
		public static void openUrl(){
			driverWrapper.openUrl("https://www.vroom.com/");
		}
		
		public static void search(String txt){
			WebElement search = driverWrapper.getElementByType("hero-search-input", "id");
			if (search != null){
				search.sendKeys(txt);
			}
		}
		
		public static void clickSearch(){
			WebElement searchBtn = driverWrapper.getElementByType("//button[@class='search-button']", "xpath");
			if (searchBtn != null){
				searchBtn.click();
			}
		}
		
	}
	
	public static class VroomSearchPage extends GenericPageObject{
		
		//remove the $ and convert to integer
		private static Integer convertStringPriceToInteger(String stringPrice){
			return Integer.parseInt(stringPrice.replaceAll("[^\\d.]", ""));
		}
		
		public static void sortByHighest(){
			
			driverWrapper.hoverAndClick("//div[@class='catalog-sort-dropdown']//span[@class='dropdown-heading']"
					,"//div[@class='submenu']/ul[@class='list-block']//a[contains(text(),'Highest Price')]");
					
		}
		
		
		public static void openPricefilterMenu(){
			WebElement filterTab = driverWrapper.getElementByType("//span[@class='filter-sidebar-container']//h4[contains(text(),'price')]", "xpath");
			if (filterTab != null){
				filterTab.click();
			}
		}
		
		public static void selectHighestPriceOption(){
			WebElement filterBtn = driverWrapper.getElementByType("//div[contains(text(),'Above $40K')]", "xpath");
			if (filterBtn != null){
				filterBtn.click();
			}
		}
		
		
		
		public static void waitForElemsToLoad(){
			driverWrapper.waitForElements("//article[@class='catalog-car-item ar-item']");
		}
		
		
		public static Integer getFirstElementPrice(){
			WebElement firstElem = driverWrapper.getStaleElement("//ul[@class='car-list list-unstyled']/li[1]//div[@class='price']");
			return convertStringPriceToInteger(firstElem.getText());
		}
		
		 
		
		public static Integer getHighestPricedCar(){
			List<WebElement> elems = driverWrapper.findListOfElements("//article[@class='catalog-car-item ar-item']//div[@class='price']");
			List<Integer> lList = new ArrayList<Integer>();
			System.out.println("elems count:" + elems.size());
			
			if (elems != null){
				for (int i=0; i<elems.size();i++){
					lList.add(convertStringPriceToInteger(elems.get(i).getText()));
				}
			}
			System.out.println("lList count:" + lList.size());
			Collections.sort(lList);
			return lList.get(lList.size()-1);
		}
	}
}

