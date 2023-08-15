import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CheckOpenedLinksTest {
    private WebDriver driver;
    private String username = "admin";
    private String password = "admin";
    public void authorize(){
        driver.get("http://192.168.1.19/litecart/admin");
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
    }
    @BeforeEach
    public void start(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(35,TimeUnit.SECONDS);
    }
    @Test
    public void openedLinks(){
        authorize();
        driver.findElement(By.linkText("Countries")).click();
        driver.findElement(By.cssSelector("a[class=button]")).click();

        WebElement form = driver.findElement(By.cssSelector("form[method=post]"));
        List<WebElement> listLinks = form.findElements(By.cssSelector("a[target=_blank]"));

        for( WebElement link : listLinks ){
            link.click();
            String[] windowList = driver.getWindowHandles().toArray(new String[2]);
            driver.switchTo().window(windowList[1]);
            System.out.println("title: " + driver.findElement(By.tagName("title")).getAttribute("textContent"));
            driver.close();
            driver.switchTo().window(windowList[0]);
        }
    }
    @AfterEach
    public void close(){
        driver.quit();
        driver = null;
    }
}
