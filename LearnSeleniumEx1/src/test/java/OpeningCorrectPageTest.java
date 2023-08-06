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
    public int getColor(String stringColor, String desiredColor, String browser){
        String step1 = new String();
        if (browser.equals("Chrome") || browser.equals("Edge")) {
            step1 = stringColor.replace("rgba(", "");
        } else if (browser.equals("Firefox")) {
            step1 = stringColor.replace("rgb(", "");
        }
         String step2 = step1.replace(")", "");
         String[] colors = step2.split(",");

        if (desiredColor.equals("Red")){
            return Integer.parseInt(colors[0]);
        } else if (desiredColor.equals("Green")) {
            return Integer.parseInt(colors[1].replace(" ",""));
        } else if (desiredColor.equals("Blue")) {
            return Integer.parseInt(colors[2].replace(" ",""));
        }
        return 0;
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



        String regularPriceMainPageColor = regularPrise.getCssValue("color");
        int regularPriceMainPageRed = getColor(regularPriceMainPageColor, "Red", browserName);
        int regularPriceMainPageGreen = getColor(regularPriceMainPageColor, "Green", browserName);
        int regularPriceMainPageBlue = getColor(regularPriceMainPageColor, "Blue", browserName);

        //3) checking regular price on main page
        assertTrue( regularPriceMainPageRed == regularPriceMainPageGreen &&
                regularPriceMainPageGreen == regularPriceMainPageBlue );
        assertEquals("line-through", regularPrise.getCssValue("text-decoration-line"));

        String campaignsPriceMainPageColor = campaignsPrise.getCssValue("color");
        int campaignsPriceMainPageColorGreen = getColor( campaignsPriceMainPageColor,"Green", browserName);
        int campaignsPriceMainPageColorBlue = getColor( campaignsPriceMainPageColor,"Blue", browserName);

        //4) checking campaigns price on main page
        assertTrue( campaignsPriceMainPageColorGreen == 0 && campaignsPriceMainPageColorBlue == 0);
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

        String regularPricePPColor = regularPrisePP.getCssValue("color");
        int regularPricePPRed = getColor(regularPricePPColor, "Red", browserName);
        int regularPricePPGreen = getColor(regularPricePPColor, "Green", browserName);
        int regularPricePPBlue = getColor(regularPricePPColor, "Blue", browserName);
        //3) checking regular price on product page
        assertTrue( regularPricePPRed == regularPricePPGreen && regularPricePPRed == regularPricePPBlue);
        assertEquals("line-through", regularPrisePP.getCssValue("text-decoration-line"));

        String campaignsPricePPColor = campaignsPrisePP.getCssValue("color");
        int campaignPricePPColorGreen = getColor(campaignsPricePPColor, "Green", browserName);
        int campaignPricePPColorBlue = getColor(campaignsPricePPColor, "Blue", browserName);

        //4) checking campaigns price on main page
        assertTrue( campaignPricePPColorGreen == 0 && campaignPricePPColorBlue == 0);
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
