package com.wrath.selenium.browser;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Browser {

    private WebDriver driver;

    public Browser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
    }

    public WebDriverWait getWait(int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    public Actions getActions() {
        return new Actions(driver);
    }

    public void getPage(String url) {
        driver.get(url);
    }

    public void close() {
        driver.quit();
    }

    public WebElement findElementByCssSelector(String selector) {
        return driver.findElement(By.cssSelector(selector));
    }

    public WebElement findElementByTagName(String tagName) {
        return driver.findElement(By.tagName(tagName));
    }

    public WebElement findElementById(String id) {
        return driver.findElement(By.id(id));
    }

}
