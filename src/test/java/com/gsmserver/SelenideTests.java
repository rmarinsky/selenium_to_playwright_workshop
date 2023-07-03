package com.gsmserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTests {

    @Test
    @DisplayName("Order product from the product page and check cart")
    void orderProductFromTheProductPageAndCheckCart() {
        var targetProductName = "Sigma Plus Box";

        open("https://gsmserver.com/en/");

        $("#searchbox input").setValue(targetProductName).pressEnter();
        $("[space='page/product/filter/amount']").shouldBe(visible);

        $("[name=product-card] [title='Sigma Plus Box']").click();

        $("[space='page/product/price-block'] .btn-buy").click();
        $("[space='page/product/price-block'] [name='quantity']").shouldBe(visible);

        $("[space='widget/cart/header-block']").click();

        $("[space='component/product/tiny'] [name='title']").shouldHave(text(targetProductName));
    }

}
