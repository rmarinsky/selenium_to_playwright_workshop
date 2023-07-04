package com.gsmserver;

import static com.codeborne.selenide.Selenide.$;

public class HomePage {

    public void searchForProductByName(String productName) {
        $("#searchbox input")
                .setValue(productName)
                .pressEnter();
    }

}
