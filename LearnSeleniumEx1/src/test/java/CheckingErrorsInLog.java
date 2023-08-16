import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class CheckingErrorsInLog {
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
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    @Test
    public void checkingLog(){
        authorize();
        driver.findElement(By.linkText("Catalog")).click();
        driver.findElement(By.xpath("//a[contains(@href,'category_id=1')]")).click();
        driver.findElement(By.xpath("//a[contains(@href,'category_id=2')]")).click();

        WebElement table = driver.findElement(By.xpath("//table[@class='dataTable']"));
        List<WebElement> productList = table.findElements(By.cssSelector("a[href*=product_id]:not([title=Edit])"));

        for( WebElement product : productList ){
           String uri = product.getAttribute("href");
            System.out.println("URI: " + product.getAttribute("href"));
            String catalogHandle = driver.getWindowHandle();
            driver.switchTo().newWindow(WindowType.TAB);
            driver.get(uri);
            List<LogEntry> logList = driver.manage().logs().get("browser").getAll();
            try{
                assertEquals(0, logList.size() );
            }catch (AssertionError e){
                for(LogEntry log : logList){
                    System.out.println("Alarm!!!. Error message: " + log);
                }
            }
            System.out.println("Title: " + driver.findElement(By.tagName("h1")).getText());
            driver.close();
            driver.switchTo().window(catalogHandle);
        }
    }
    @AfterEach
    public void close(){
        driver.quit();
        driver = null;
    }
}