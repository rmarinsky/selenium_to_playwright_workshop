package com.gsmserver;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.util.concurrent.ConcurrentHashMap;

public class AIPWrapper {

    private static class PlaywrightInstance {
        Playwright playwright;
        BrowserContext context;
        Page page;
    }

    private static final ConcurrentHashMap<Long, PlaywrightInstance> instances = new ConcurrentHashMap<>();

    /**
     * Initializes Playwright, BrowserContext and Page for the current thread.
     */
    public static void get() {
        PlaywrightInstance instance = new PlaywrightInstance();
        instance.playwright = Playwright.create();
        instance.context = instance.playwright.chromium().launch().newContext();
        instance.page = instance.context.newPage();
        instances.put(Thread.currentThread().getId(), instance);
    }

    /**
     * Clean up method to close playwright instance associated with current thread.
     */
    public static void cleanup() {
        PlaywrightInstance instance = instances.remove(Thread.currentThread().getId());
        if (instance != null) {
            instance.page.close();
            instance.context.close();
            instance.playwright.close();
        }
    }

    /**
     * Get BrowserContext instance for current thread.
     */
    public static BrowserContext context() {
        PlaywrightInstance instance = instances.get(Thread.currentThread().getId());
        return instance == null ? null : instance.context;
    }

    /**
     * Get Page instance for current thread.
     */
    public static Page page() {
        PlaywrightInstance instance = instances.get(Thread.currentThread().getId());
        return instance == null ? null : instance.page;
    }

    public static void open(String url) {
        page().navigate(url);
    }



}
