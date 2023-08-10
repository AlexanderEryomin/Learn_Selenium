import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CreateAccountTest {
    private WebDriver driver;
    @BeforeEach
    public void startBrowser(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    @Test
    public void createAccountLitecart(){

        driver.get("http://192.168.1.19/litecart/");
        //1. Creating user
        driver.findElement(By.linkText("New customers click here")).click();

        int randomInt = 10 + (int) (Math.random()* 99);
        String randomIntToString = Integer.toString(randomInt);

        driver.findElement(By.name("tax_id")).sendKeys(randomIntToString);
        driver.findElement(By.name("company")).sendKeys("testCompany" + randomIntToString);
        driver.findElement(By.name("firstname")).sendKeys("testFirstname" + randomIntToString );
        driver.findElement(By.name("lastname")).sendKeys("testLastname" + randomIntToString);
        driver.findElement(By.name("address1")).sendKeys("testAddress1" + randomIntToString);
        driver.findElement(By.name("address2")).sendKeys("testAddress2" + randomIntToString);
        driver.findElement(By.className("select2-selection__rendered")).click();
        driver.findElement(By.className("select2-search__field")).sendKeys("United States" + Keys.ENTER);

        WebElement selectZone = driver.findElement(By.cssSelector("select[name=zone_code]"));
        Select selectZoneCode = new Select(selectZone);
        List<WebElement> zoneList = selectZoneCode.getOptions();
        int zoneListSize = zoneList.size();
        int randomZoneIndex = 1 + (int)(Math.random()* zoneListSize);
        selectZoneCode.selectByIndex(randomZoneIndex);

        driver.findElement(By.name("postcode")).sendKeys("11122");
        driver.findElement(By.name("city")).sendKeys("TestCity" + randomIntToString);
        String testEmail = "testemail" + randomIntToString + "@gmail.com";
        driver.findElement(By.name("email")).sendKeys(testEmail);
        driver.findElement(By.name("phone")).sendKeys("89998887766");
        String testPassword = "1111";
        driver.findElement(By.name("password")).sendKeys(testPassword);
        driver.findElement(By.name("confirmed_password")).sendKeys(testPassword);
        driver.findElement(By.name("create_account")).click();
        //2. Logout
        driver.findElement(By.linkText("Logout")).click();
        //3. Authentification user
        driver.findElement(By.name("email")).sendKeys(testEmail);
        driver.findElement(By.name("password")).sendKeys(testPassword);
        driver.findElement(By.name("login")).click();
        //4. Second logout
        driver.findElement(By.linkText("Logout")).click();


    }
    @AfterEach
    public void closeBrowser(){
        driver.quit();
        driver = null;
    }
}
