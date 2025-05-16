package com.wrath.selenium.krx;

import com.wrath.selenium.config.Config;
import com.wrath.selenium.browser.Browser;

public class Krx {

    private Config config;

    private Browser browser;

    public Krx(String configFile) {
        browser = new Browser();
        config = Config.loadFromJson(configFile);
    }

    public void getMain() {
        browser.getPage(config.getUrl());
    }

    public void close() {
        browser.close();
    }

}
