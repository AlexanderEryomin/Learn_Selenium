import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.attributeContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement;

public class WorkWithCartTest {
    private WebDriver driver;
    private WebDriverWait wait;
    int numberProducts = 3;
    @BeforeEach
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Test
    public void workWithCart(){
        driver.get("http://192.168.1.19/litecart/");
        int step = 0;
        WebElement addedProduct;
        while( step < numberProducts ){
            addedProduct = driver.findElement(By.className("product"));
            addedProduct.findElement(By.className("name")).click();
            try{
                WebElement elementSelect = driver.findElement(By.tagName("select"));
                Select select = new Select(elementSelect);
                select.selectByIndex(1);
            } catch (NoSuchElementException ex) {
            }
            driver.findElement(By.name("add_cart_product")).click();
            WebElement numberProductCart = driver.findElement(By.className("quantity"));
            step+=1;
            String count = Integer.toString(step);
            wait.until(textToBePresentInElement(numberProductCart,count));
            driver.findElement(By.id("logotype-wrapper")).click();
        }
        driver.findElement(By.className("link")).click();
        while ( numberProducts > 0 ){
            if ( numberProducts > 1){
                driver.findElement(By.xpath("//li[@class='shortcut']")).click();
            }
            WebElement dataTable = driver.findElement(By.className("dataTable"));
            WebElement totalSum = dataTable.findElement(By.xpath("//tr[@class='footer']/td[2]"));
            driver.findElement(By.name("remove_cart_item")).click();
            wait.until(ExpectedConditions.stalenessOf(totalSum));
            numberProducts-=1;
        }

        String GameOver = "There are no items in your cart.";
        assertEquals(GameOver,driver.findElement(By.tagName("em")).getText());
    }
    @AfterEach
    public void close(){
        driver.quit();
        driver = null;
    }
}
