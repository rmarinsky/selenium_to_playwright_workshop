package com.gsmserver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SelenideTests {

    @Test
    @DisplayName("Open product page and check product name in the cart")
    void open() {
        Configuration.pageLoadTimeout = 100000;
        Selenide.open("https://gsmserver.com/en/");
        var targetProductName = "Sigma Plus Box";
        $("#searchbox input")
                .setValue(targetProductName)
                .pressEnter();

        $("[space='page/product/filter/amount']").shouldBe(visible);
        $("[name=product-card] [title='Sigma Plus Box']").click();

        $("[space='page/product/price-block'] .btn-buy").click();
        $("[space='page/product/price-block'] [name='quantity']").shouldBe(visible);

        $("[space='widget/cart/header-block']").click();

        $("[space='component/product/tiny'] [name='title']").shouldHave(text(targetProductName));
    }

}
