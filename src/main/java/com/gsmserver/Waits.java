package com.gsmserver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Waits {

    Duration waitDuration = Duration.ofSeconds(10);
    Duration pollingInterval = Duration.ofMillis(100);

    private WebElement waitForElement(WebDriver driver, By targetElement) {
        return new WebDriverWait(driver, waitDuration, pollingInterval)
                .until(visibilityOfElementLocated(targetElement));
    }

}
