import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

public class CheckingStickersTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String testURL = "http://192.168.1.19/litecart/en/";
    @BeforeEach
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    @Test
    public void checkSticker(){
        driver.get(testURL);
        List<WebElement> productList = driver.findElements(By.className("product"));
        for (WebElement product : productList){
            try{
                //Checking for stickers
                product.findElement(By.className("sticker"));
                //Checking the number of stickers
                assertEquals(1, product.findElements(By.className("sticker")).size());
            } catch (NoSuchElementException ex) {
            }

        }
    }
    @AfterEach
    public void close(){
        driver.quit();
        driver = null;
    }
}
