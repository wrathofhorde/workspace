package com.wrath.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.wrath.selenium.config.Config;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Main {
    public static void testSelenium(Config config) {
        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();
        driver.get(config.getUrl());

        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("셀레니움");
        searchBox.sendKeys(Keys.RETURN);

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.quit();
    }

    public static void main(String[] args) {
        try {
            Config config = Config.loadFromJson("./config.json");
            System.out.println(config.getUrl());
            testSelenium(config);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}