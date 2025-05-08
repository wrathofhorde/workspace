package com.wrath.selenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.wrath.selenium.trading.Trading;
import com.wrath.selenium.config.Config;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Main {
    public static void testSelenium(Config config) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);
        driver.get(config.getUrl());

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));
        searchBox.sendKeys("제미나이");
        searchBox.sendKeys(Keys.RETURN);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search")));

        driver.quit();
    }

    public static void main(String[] args) {
        String configFile = "config-test.json";

        try {
            Trading trading = new Trading(configFile);

            trading.goMainPage();
            trading.moveExchangePage();
            Thread.sleep(10000);

            trading.moveLoginPage();
            Thread.sleep(5000);

            trading.inputUserInfo();
            Thread.sleep(10000);

            trading.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}