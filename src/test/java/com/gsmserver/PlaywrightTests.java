package com.gsmserver;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class PlaywrightTests {

    private Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeEach
    public void beforeEach() {
    }

    @AfterEach
    public void afterEach() {
        browser.close();
    }

    @Test
    void orderProductFromTheProductPageAndCheckCart() {
        String targetProductName = "Sigma Plus Box";

        page.navigate("https://gsmserver.com/en/");

        Locator searchInput = page.locator("#searchbox input");
        searchInput.type(targetProductName);
        searchInput.press("Enter");

        page.locator("[name=product-card] [title='Sigma Plus Box']").click();

        page.locator("[space='page/product/price-block'] .btn-buy").first().click();

        page.locator("[space='widget/cart/header-block']").click();

        Locator productInCart = page.locator("[space='component/product/tiny'] [name='title']");
        assertThat(productInCart).hasText(targetProductName);
    }


}
