package utils;

import org.openqa.selenium.support.PageFactory;

public abstract class PageObject {
    private PageObject(){
        PageFactory.initElements(DriverManager.getWebDriver(), this);
    }
}
