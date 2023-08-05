import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class OpeningCorrectPageTest {
    private WebDriver driver;
    public void startChrome(){
        driver = new ChromeDriver();
    }
    public void startFirefox(){
        driver = new FirefoxDriver();
    }
    public void startMicrosoftEdge(){
        driver = new EdgeDriver();
    }
    public void checkingElementStyleForFirefox(){

    }
    @ParameterizedTest
    @ValueSource(strings = {"Chrome", "Firefox", "Edge"})
    public void openingCorrectPageChrome(String  browserName){

        if ( browserName.equals("Chrome") ) startChrome();
        if ( browserName.equals("Firefox") ) startFirefox();
        if ( browserName.equals("Edge") ) startMicrosoftEdge();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://192.168.1.19/litecart");

        WebElement Campaigns = driver.findElement(By.id("box-campaigns"));
        WebElement firstItemFromCampaigns = Campaigns.findElement(By.tagName("li"));
        WebElement itemName = firstItemFromCampaigns.findElement(By.className("name"));
        WebElement regularPrise = firstItemFromCampaigns.findElement(By.className("regular-price"));
        WebElement campaignsPrise = firstItemFromCampaigns.findElement(By.className("campaign-price"));


        String nameFromMainPage = itemName.getAttribute("textContent");
        String regularPriseFromMainPage = regularPrise.getAttribute("textContent");
        String campaignsPriseFromMainPage = campaignsPrise.getAttribute("textContent");
        String fontSizeRegularPriseFromMainPage = regularPrise.getCssValue("font-size");
        String fontSizeCampaignsPriseFromMainPage = campaignsPrise.getCssValue("font-size");
        String fontSizeRegularPriseString = fontSizeRegularPriseFromMainPage.replace("px", "");
        String fontSizeCampaingPriseString = fontSizeCampaignsPriseFromMainPage.replace("px", "");
        float fontSizeRegularPriseFromMainPageFloat = Float.parseFloat(fontSizeRegularPriseString);
        float fontSizeCampaignsPriseFromMainPageFloat = Float.parseFloat(fontSizeCampaingPriseString);

        //3) checking regular price on main page
        if (! browserName.equals("Firefox"))
            assertEquals("rgba(119, 119, 119, 1)",regularPrise.getCssValue("color"));
        else
            assertEquals("rgb(119, 119, 119)",regularPrise.getCssValue("color"));
        assertEquals("line-through", regularPrise.getCssValue("text-decoration-line"));
        //4) checking campaigns price on main page
        if (! browserName.equals("Firefox"))
            assertEquals("rgba(204, 0, 0, 1)", campaignsPrise.getCssValue("color"));
        else
            assertEquals("rgb(204, 0, 0)", campaignsPrise.getCssValue("color"));
        assertEquals("STRONG", campaignsPrise.getAttribute("tagName"));
        //5) checking size
        assertTrue(fontSizeCampaignsPriseFromMainPageFloat > fontSizeRegularPriseFromMainPageFloat);


        firstItemFromCampaigns.click();

        WebElement productName = driver.findElement(By.cssSelector("h1[class=title][itemprop=name]"));
        WebElement regularPrisePP = driver.findElement(By.className("regular-price"));
        WebElement campaignsPrisePP = driver.findElement(By.className("campaign-price"));

        String nameFromProductPage = productName.getAttribute("textContent");
        String regularPriseFromProductPage = regularPrisePP.getAttribute("textContent");
        String campaignPriseFromProductPage = campaignsPrisePP.getAttribute("textContent");
        String fontSizeRegularPriseProductPage = regularPrisePP.getCssValue("font-size");
        String fontSizeCampaignsPriseProductPage = campaignsPrisePP.getCssValue("font-size");
        String fontSizeRegularPrisePPString = fontSizeRegularPriseProductPage.replace("px", "");
        String fontSizeCampaingPrisePPString = fontSizeCampaignsPriseProductPage.replace("px", "");
        float fontSizeRegularPriseFromProductPageFloat = Float.parseFloat(fontSizeRegularPrisePPString);
        float fontSizeCampaignsPriseFromProductPageFloat = Float.parseFloat(fontSizeCampaingPrisePPString);

        //1) checking product name
        assertTrue(nameFromMainPage.equals(nameFromProductPage));
        //2) checking product prises
        assertEquals(regularPriseFromMainPage, regularPriseFromProductPage);
        assertEquals(campaignsPriseFromMainPage, campaignPriseFromProductPage);
        //3) checking regular price on product page
        if (! browserName.equals("Firefox"))
            assertEquals("rgba(102, 102, 102, 1)", regularPrisePP.getCssValue("color"));
        else
            assertEquals("rgb(102, 102, 102)", regularPrisePP.getCssValue("color"));
        assertEquals("line-through", regularPrisePP.getCssValue("text-decoration-line"));
        //4) checking campaigns price on main page
        if (! browserName.equals("Firefox"))
            assertEquals("rgba(204, 0, 0, 1)", campaignsPrisePP.getCssValue("color"));
        else
            assertEquals("rgb(204, 0, 0)", campaignsPrisePP.getCssValue("color"));
        assertEquals("STRONG", campaignsPrisePP.getAttribute("tagName"));
        //5) checking size
        assertTrue( fontSizeCampaignsPriseFromProductPageFloat > fontSizeRegularPriseFromProductPageFloat);
    }
    @AfterEach
    public void close(){
        driver.quit();
        driver = null;
    }
}
