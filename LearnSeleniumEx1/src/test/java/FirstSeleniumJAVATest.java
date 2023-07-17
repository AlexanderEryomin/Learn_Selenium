import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static junit.framework.TestCase.assertEquals;

public class FirstSeleniumJAVATest {
    private WebDriver driver;
    @Test
    public void firstTest(){
        driver = new ChromeDriver();

        driver.get("https://www.mail.ru/");
        String actualAttribute;
        driver.findElement(By.cssSelector("[href=\"//mail.ru\"]"));
        actualAttribute = driver.findElement(By.cssSelector("[href=\"//mail.ru\"]")).getAttribute("href");
        assertEquals("https://mail.ru/", actualAttribute);

       driver.quit();
       driver = null;
    }

}
