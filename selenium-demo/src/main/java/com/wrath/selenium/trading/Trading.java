package com.wrath.selenium.trading;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.wrath.selenium.config.Config;
import com.wrath.selenium.browser.Browser;

public class Trading {

    private Config config;

    private Browser browser;

    public Trading(String configFile) {
        browser = new Browser();
        config = Config.loadFromJson(configFile);
    }

    private void removePopUp() {
        boolean isPopup = true;

        while (isPopup) {
            try {
                WebElement popup = browser.findElementByCssSelector(config.getMainPopup());
                List<WebElement> buttons = popup
                        .findElement(By.id(config.getMainPopupButton()))
                        .findElements(By.tagName("button"));
                WebElement buttonClose = buttons.get(buttons.size() - 1);
                buttonClose.sendKeys(Keys.RETURN);

                Thread.sleep(1000);
            } catch (NoSuchElementException e) {
                isPopup = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void goMainPage() {
        browser.getPage(config.getUrl());
        removePopUp();
    }

    public void moveExchangePage() {
        WebElement aTag = browser
                .findElementByTagName("nav")
                .findElement(By.tagName("a"));
        aTag.sendKeys(Keys.RETURN);
    }

    public void moveLoginPage() {
        WebElement loginButton = browser
                .findElementByTagName("header")
                .findElement(By.cssSelector("div.login-button"));

        Actions action = browser.getActions();
        action.moveToElement(loginButton, 0, 0).click().perform();
    }

    public void inputUserInfo() {
        try {
            WebDriverWait wait = browser.getWait(30);

            WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
            email.sendKeys(config.getEmail());
            Thread.sleep(1000);

            WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
            password.sendKeys(config.getPassword());
            Thread.sleep(1000);

            WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginBtn")));
            System.out.println(login);
            // login.sendKeys(Keys.RETURN);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void close() {
        browser.close();
    }

}
