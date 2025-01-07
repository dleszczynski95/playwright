package pages;

import com.microsoft.playwright.Page;

public abstract class BasePage {
    protected Page page;
    Page.WaitForConditionOptions timeout;


    public BasePage(Page page) {
        this.page = page;
        timeout = new Page.WaitForConditionOptions().setTimeout(5000);
    }
}
