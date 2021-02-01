package com.automation.qa.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.github.bonigarcia.wdm.WebDriverManager;

public class GooglePage {
	public static WebDriver driver;
	public static Properties prop;
	
	WebDriverWait wait = new WebDriverWait(driver, 20);
	Actions action = new Actions(driver);
	JavascriptExecutor js = ((JavascriptExecutor) driver);

	public static String EXCEL_FILE_LOCATION = "Data/DataValues.xlsx";

    
    
    public static void getTextFromDoodle() throws InterruptedException, IOException {
    	System.out.println("Get the link from first doodle");
    	
    	WebDriverManager.chromedriver().setup();
    	
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
    	driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.get("https://www.google.com/");
		WebElement FeelingLucky = driver.findElement(By.xpath("(//input[contains(@value,'Feeling Lucky')])[2]"));
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(FeelingLucky));
		FeelingLucky.click();
		
		WebElement DoodleLink = driver.findElement(By.xpath("//div[@id='highlight']/ul/li/div/div/div"));
		wait.until(ExpectedConditions.elementToBeClickable(DoodleLink));
		String text = DoodleLink.getText().toString();
		System.out.println("Text is ---> "+text);
		
		File source = new File(EXCEL_FILE_LOCATION);
    	FileInputStream input = new FileInputStream(source);
    	XSSFWorkbook wb = new XSSFWorkbook(input);
    	XSSFSheet sheet = wb.getSheetAt(0);
    	sheet.getRow(1).createCell(0).setCellValue(text);
    	FileOutputStream output = new FileOutputStream(source);
    	wb.write(output);
    	wb.close();
    }
    
    public static void main (String arg[]) throws InterruptedException, IOException {
    	getTextFromDoodle();
    }
    
}
