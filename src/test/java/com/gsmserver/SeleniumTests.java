package com.gsmserver;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class SeleniumTests {

    private WebDriver driver;

    @BeforeEach
    void beforeEach() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void afterEach() {
        driver.quit();
    }

    @Test
    @DisplayName("Order product from product page and check cart")
    void orderProductFromProductPageAndCheckCart() {
        driver.get("https://gsmserver.com/en/");
        var targetProductName = "Sigma Plus Box";
        driver.findElement(cssSelector("#searchbox input")).sendKeys(targetProductName + Keys.ENTER);

        waitForElement(driver, cssSelector("[space='page/product/filter/amount']"));
        driver.findElement(cssSelector("[name=product-card] [title='Sigma Plus Box']")).click();

        waitForElement(driver, cssSelector("[space='page/product/price-block'] .btn-buy")).click();
        waitForElement(driver, cssSelector("[space='page/product/price-block'] [name='quantity']"));

        driver.findElement(cssSelector("[space='widget/cart/header-block']")).click();

        var actualProductName = waitForElement(driver, cssSelector("[space='component/product/tiny'] [name='title']"))
                .getText();

        Assertions.assertEquals(targetProductName, actualProductName);
    }

    private WebElement waitForElement(WebDriver driver, By by) {
        return new WebDriverWait(driver, ofSeconds(10), ofMillis(100)).until(visibilityOfElementLocated(by));
    }

}
