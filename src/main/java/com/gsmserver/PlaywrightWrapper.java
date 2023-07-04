package com.gsmserver;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

import java.util.concurrent.ConcurrentHashMap;

public class PlaywrightWrapper {

    public static class ContextAndPage {

        private final BrowserContext context;
        private final Page page;

        public ContextAndPage(BrowserContext context, Page page) {
            this.context = context;
            this.page = page;
        }

        public BrowserContext context() {
            return context;
        }

        public Page page() {
            return page;
        }

    }

    private static final ConcurrentHashMap<Long, ContextAndPage> storage = new ConcurrentHashMap<>();

    public static ContextAndPage get() {
        Long threadId = Thread.currentThread().getId();

        return storage.computeIfAbsent(threadId, k -> {
            Browser browser = Playwright.create().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            return new ContextAndPage(context, page);
        });
    }


    // Call this when you're done
    public static void close() {
        Long threadId = Thread.currentThread().getId();
        ContextAndPage contextAndPage = storage.get(threadId);

        if (contextAndPage != null) {
            contextAndPage.context().close();
            storage.remove(threadId);
        }
    }

    public static ElementHandleWrapper find(String selector) {
        return new ElementHandleWrapper(get().page.locator(selector).first());
    }

    public static void open(String url) {
        get().page.navigate(url);
    }


    public static class ElementHandleWrapper {

        private final Locator element;

        public ElementHandleWrapper(Locator element) {
            this.element = element;
        }

        public ElementHandleWrapper setValue(String value) {
            element.fill(value);
            return this;
        }

        public ElementHandleWrapper press(String key) {
            element.press(key);
            return this;
        }

        public void click() {
            element.click();
        }

        public ElementHandleWrapper shouldBeVisible() {
            PlaywrightAssertions.assertThat( element).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
            // you can modify the timeout value as you wish
            return this;
        }

        public ElementHandleWrapper shouldHaveText(String targetProductName) {
            PlaywrightAssertions.assertThat(element).containsText(targetProductName);
            return this;
        }

    }

}
