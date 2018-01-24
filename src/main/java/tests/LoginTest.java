package tests;

import org.junit.Test;

import pageObjects.PageObjects.VroomHomePage;

public class LoginTest extends BasicTest{

	//the long way
	@Test
	public void testHighestPriceLong() throws InterruptedException{
		//opens the page
		VroomHomePage.openUrl();
		
		//enter search for the page
		VroomHomePage.search("bmw");
		
//		//click search button
//		VroomHomePage.clickSearch();
//			
//		//open price filter menu
//		VroomSearchPage.openPricefilterMenu();
//		
//		//select highest option
//		VroomSearchPage.selectHighestPriceOption();
//		
//		
//		//get Highest price
//		Integer carPrice = VroomSearchPage.getHighestPricedCar();
//		
//		//do the assert
//		Assert.assertTrue("The highest car is not above 300,000, it's " + carPrice, carPrice > 300000 ); 
//	
			
	}
	
	//the short way
	@Test
	public void testHighestPriceShort() throws InterruptedException{
		//opens the page
		VroomHomePage.openUrl();
		
		//enter search for the page
		VroomHomePage.search("Mother fucker");
		
		//click search button
//		VroomHomePage.clickSearch();
//		
//		//sort by highest
//		VroomSearchPage.sortByHighest();
//			
//		//get first element price with stale 
//		Integer carPrice = VroomSearchPage.getFirstElementPrice();
//		
//		//do the assert
//		Assert.assertTrue("The highest car is not above 300,000, it's " + carPrice, carPrice > 300000 ); 
	
	}

}
