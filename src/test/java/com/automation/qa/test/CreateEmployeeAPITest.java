package com.automation.qa.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.automation.qa.base.ParseDynamicJson;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateEmployeeAPITest  {
	
	
	ParseDynamicJson parseDynamicJson;
	public static String Url = "http://dummy.restapiexample.com/api/v1/create";

	public static String EmpName = getRandomString();

	public static String EXCEL_FILE_LOCATION = "Data/DataValues.xlsx";
	
	
	public static String getRandomString() {

	    int stringLen = 7;
	    boolean allowChars = true;
	    boolean allowNumbers = true;
	    String randomString = RandomStringUtils.random(stringLen, allowChars, allowNumbers);
	    return randomString;
	}
	
	public static String getRandomNumbers() {

	    int stringLen = 8;
	    boolean allowChars = false;
	    boolean allowNumbers = true;
	    String randomString = RandomStringUtils.random(stringLen, allowChars, allowNumbers);
	    return randomString;
	}
	
	public static void CreateEmp() throws IOException {
		
		FileInputStream fis = new FileInputStream(EXCEL_FILE_LOCATION);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		DataFormatter dataFormatter = new DataFormatter();
		XSSFSheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(0);
		Cell cell = row.getCell(0);
		String storedValue = sheet.getRow(1).getCell(0).getStringCellValue();
		
		String EmpFullName = storedValue+" " +EmpName;
		
		Response resp = RestAssured.given()
				.contentType("application/json\r\n")
				.body("{\"name\":\""+EmpFullName+"\",\"salary\":\"123\",\"age\":\"23\"}")
				.post(Url)
				.then().contentType(ContentType.JSON).extract().response();
		int statusCode = resp.getStatusCode();
		System.out.println("Response Code is " +resp.getStatusCode());
		String inputJson = resp.getBody().jsonPath().prettify();
		System.out.println("Body is ----> " +inputJson);
		
		Map<String, Object> EmpDetails = resp.jsonPath().getMap("data");
		
		String FullName = (String) EmpDetails.get("name");
		System.out.println("Full name of employee is ---> "+FullName);
		
		try {
			if(FullName.contains(storedValue)) {
				System.out.println("First name is same");
			}
		}catch(Exception e) {
			System.out.println("Full name is mismatched");
		}
		
		
		
	}
	
	public static void main (String arg[]) throws IOException, InterruptedException {
		CreateEmp();
	}
	

}
