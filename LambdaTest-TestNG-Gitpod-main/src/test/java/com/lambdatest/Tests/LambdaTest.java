package com.lambdatest.Tests;

//This is maven project
import org.testng.annotations.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;

public class LambdaTest {

	public RemoteWebDriver driver = null;
	String username = "visheshmittal97part2";
	String accessKey = "01iLdsmmRshyBqj8PZGsekyWIb2SgHpUdaxLatzYuN3x1swEu9";

	@BeforeTest
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platform", "Windows 10");
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("version", "87.0"); // If this cap isn't specified, it will just get the any
														// available one
		capabilities.setCapability("resolution", "1024x768");
		capabilities.setCapability("build", "First Test");
		capabilities.setCapability("name", "Sample Test");
		capabilities.setCapability("network", true); // To enable network logs
		capabilities.setCapability("visual", true); // To enable step by step screenshot
		capabilities.setCapability("video", true); // To enable video recording
		capabilities.setCapability("console", true); // To capture console logs

		try {
			driver = new RemoteWebDriver(
					new URL("https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			System.out.println("Invalid grid URL");
		}
	}

	@Test(enabled = true)
	public void testScript() throws Exception {
		try {
			driver.get("https://www.amazon.in");
			Thread.sleep(2500);
			driver.findElement(By.id("twotabsearchtextbox")).sendKeys("LG Washing Machine");
			driver.findElement(By.id("nav-search-submit-button")).click();
			Thread.sleep(2500);
			driver.findElement(By.xpath("//div[@id='brandsRefinements']/ul/li/span")).click();
			Thread.sleep(2500);
			List<WebElement> listOfNames = driver
					.findElements(By.xpath("//span[@class='a-size-medium a-color-base a-text-normal']"));
			List<WebElement> listOfPrices = driver.findElements(By.xpath("//span[@class='a-price-whole']"));
			HashMap<String, Integer> List = new HashMap<String, Integer>();
			for (int i = 0; i < listOfNames.size(); i++) {
				String price = listOfPrices.get(i).getText().replaceAll(",", "");
				Integer p = Integer.parseInt(price);
				List.put(listOfNames.get(i).getText(), (p));
			}
			Map<String, Integer> sortedList = List.entrySet().stream()
					.sorted(Collections.reverseOrder(Entry.comparingByValue()))
					.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
			sortedList.forEach((key, value) -> System.out.println(value + "   :   "  + key));

			driver.quit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
