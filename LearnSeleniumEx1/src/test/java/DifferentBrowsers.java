import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DifferentBrowsers {
    private WebDriver driver;
    @Test
    public void startChromeBrowser(){
        driver = new ChromeDriver();
        driver.get("http://192.168.1.19/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }
    @Test
    public void startFirefoxBrowser(){
        driver = new FirefoxDriver();
        driver.get("http://192.168.1.19/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }
    @Test
    public void startMicrosoftEdgeBrowser(){
        driver = new EdgeDriver();
        driver.get("http://192.168.1.19/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }
    @AfterEach
    public void close(){
        driver.quit();
        driver =null;
    }
}
