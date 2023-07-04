package com.gsmserver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.gsmserver.PlaywrightWrapper.find;
import static com.gsmserver.PlaywrightWrapper.open;

public class PlaywrightTest {

    @Test
    public void testPlaywrightActions() {
        String targetProductName = "Sigma Plus Box";

        open("https://gsmserver.com/en/");

        find("#searchbox input").setValue(targetProductName).press("Enter");

        find("[space='page/product/filter/amount']").shouldBeVisible();
        find("[name='product-card'] [title='Sigma Plus Box']").click();

        find("[space='page/product/price-block'] .btn-buy").click();
        find("[space='page/product/price-block'] [name='quantity']").shouldBeVisible();

        find("[space='widget/cart/header-block']").click();


        find("[space='component/product/tiny'] [name='title']").shouldHaveText(targetProductName);
    }

    @AfterEach
    void afterEach() {
        PlaywrightWrapper.close();
    }

}
