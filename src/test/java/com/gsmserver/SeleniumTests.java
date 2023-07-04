package com.gsmserver;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
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


    @Test
    @DisplayName("Slendie open product and cart check")
    void slendieOpenProductAndCartCheck() {
        Configuration.pageLoadTimeout = 100000;

        open("https://gsmserver.com/en/");
        String targetProductName = "Sigma Plus Box";
        $($("#searchbox input")).setValue(targetProductName + Keys.ENTER);

        $("[name=product-card] [title='Sigma Plus Box']").click();

        $("[space='page/product/price-block'] .btn-buy").click();
        $("[space='page/product/price-block'] [name='quantity']").shouldBe(visible);

        $("[space='widget/cart/header-block']").click();

        $("[space='component/product/tiny'] [name='title']").shouldHave(text(targetProductName));
    }

    private WebElement waitForElement(WebDriver driver, By by) {
        return new WebDriverWait(driver, ofSeconds(10), ofMillis(100)).until(visibilityOfElementLocated(by));
    }

}
