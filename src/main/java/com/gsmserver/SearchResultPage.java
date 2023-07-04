package com.gsmserver;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SearchResultPage {

    public SearchResultPage isLoaded() {
        $("[space='page/product/filter/amount']").shouldBe(visible);
        return this;
    }

    public void clickOnProductByName(String productName) {
        $("[name=product-card] [title='" + productName + "']").click();
    }

}
