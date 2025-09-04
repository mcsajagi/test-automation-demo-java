package seleniumTests.tests;

import Reports.ExtentTestNGListener;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import seleniumTests.SeleniumBase;

@Listeners(ExtentTestNGListener.class)
public class Tests extends SeleniumBase {

    @Test(priority = 1,
            description = "Ez a teszt ellenőrzi a márka filter dropdownt. Megnézi, hogy van-e Fender gitár benne és kiirat egy listát a márkákkal")
    public void testSpecificFilterElementsInDropDown() {
        seleniumActions.analyzeDropdownForOption("Fender");
    }

    @Test(priority = 2,
            description = "Ez a teszt ellenőrzi a márka filter dropdown kattintható-e és utána a filter gomb megnyomása után van-e eredmény")
    public void testfilterFunction() {
        seleniumActions.testFilterFunction();
    }
}