package com.gsmserver;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

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
            Browser browser = Playwright.create().chromium().launch();
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            return new ContextAndPage(context, page);
        });
    }



    // Call this when you're done
    public void close() {
        Long threadId = Thread.currentThread().getId();
        ContextAndPage contextAndPage = storage.get(threadId);

        if (contextAndPage != null) {
            contextAndPage.context().close();
            storage.remove(threadId);
        }
    }

}
