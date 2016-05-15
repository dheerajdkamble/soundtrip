package com.soundtrip.service.impl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soundtrip.dto.Event;
import com.soundtrip.service.FacebookEvent;

@Service
@Transactional
public class FacebookEventImpl implements FacebookEvent {
	
	@Override
	public void createFBEvent(Event event) {
        WebDriver driver = new FirefoxDriver();
        String baseUrl = "http://www.facebook.com";
        String tagName = "ktnpatil5@yahoo.com";
        String password = "guruofhappiness5";
 
        driver.get(baseUrl);
       // tagName = driver.findElement(By.id("email")).getTagName();
        //System.out.println(tagName);  pass
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(tagName);
        
        WebElement pass = driver.findElement(By.id("pass"));
        pass.sendKeys(password);
        
        driver.findElement(By.id("loginbutton")).click();
        
        driver.findElement(By.linkText("Events")).click();
        
        WebDriverWait wait = new WebDriverWait(driver,30);
        WebElement guru99seleniumlink;
        guru99seleniumlink= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"events_nav\"]/ul/li[5]/a/div[2]/div"))); 
        guru99seleniumlink.click();
        
        
        //driver.close();
        //System.exit(0);
    }
}
