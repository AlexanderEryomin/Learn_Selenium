import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class AuthorizationTest {
    private WebDriver driver;
    @BeforeEach
    public void start(){
       driver = new ChromeDriver();
    }
    @Test
    public void authorizTest(){

        driver.get("http://192.168.1.19/litecart/admin");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

    }
    @AfterEach
    public void over(){
        driver.quit();
        driver = null;
    }
}
