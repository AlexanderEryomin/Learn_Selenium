import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static junit.framework.TestCase.assertTrue;

public class AddProductTest {
    private WebDriver driver;
    private String username = "admin";
    private String password = "admin";
    WebDriverWait wait;
    @BeforeEach
    public void start(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait =new WebDriverWait(driver,Duration.ofSeconds(10));

    }
    public void authorization(){
        driver.get("http://192.168.1.19/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.name("login")).click();
    }
    @Test
    public void addProduct(){
        authorization();
        driver.findElement(By.cssSelector("ul.list-vertical li:nth-child(2)")).click();
        driver.findElement(By.linkText("Add New Product")).click();

        int randomInt = 10 + (int) (Math.random()* 99);
        String randomIntToString = Integer.toString(randomInt);

        //General
        driver.findElement(By.tagName("label")).click();
        String productName = "name" + randomIntToString;
        driver.findElement(By.cssSelector("input[name*=name]")).sendKeys(productName);
        driver.findElement(By.cssSelector("input[name=code]")).sendKeys("11" + randomIntToString);
        driver.findElement(By.xpath("//input[@value='1-3']")).click();
        driver.findElement(By.cssSelector("input[name=quantity]")).sendKeys("1." + randomIntToString);
        Path imagesPath = Path.of("\\duck.jpg").toAbsolutePath();
        driver.findElement(By.cssSelector("input[name*=new_images]")).sendKeys(imagesPath.toString());

        //Information
        driver.findElement(By.linkText("Information")).click();
        wait.until((WebDriver d) -> d.findElement(By.cssSelector("select[name=manufacturer_id]")));
        WebElement s = driver.findElement(By.cssSelector("select[name=manufacturer_id]"));
        Select manufacturerSelect = new Select(s);
        manufacturerSelect.selectByIndex(1);
        driver.findElement(By.cssSelector("input[name=keywords]")).sendKeys("keywordtest" + randomIntToString);
        driver.findElement(By.cssSelector("input[name*=short_description]")).sendKeys("shortdescription" + randomIntToString);
        driver.findElement(By.cssSelector("div[class=trumbowyg-editor]")).sendKeys("descriptiontest" + randomIntToString);
        driver.findElement(By.cssSelector("input[name*=head_title]")).sendKeys("headtitletest" + randomIntToString);
        driver.findElement(By.cssSelector("input[name*=meta_description]")).sendKeys("metadescription" + randomIntToString);

        //Prices
        driver.findElement(By.linkText("Prices")).click();
        wait.until((WebDriver d) -> d.findElement(By.cssSelector("input[name*=USD")));
        driver.findElement(By.cssSelector("input[name*=USD")).sendKeys("1123.22");
        driver.findElement(By.cssSelector("input[name*=EUR")).sendKeys("1156.33");

        //add product
        driver.findElement(By.cssSelector("button[type=submit]")).click();

        //checking creating product
        WebElement dataTable = driver.findElement(By.className("dataTable"));
        List<WebElement> aList = dataTable.findElements(By.cssSelector("a:not([title=Edit])"));
        boolean f = false;
        for (WebElement element : aList){
            if(productName.equals(element.getAttribute("textContent"))){
                f = true;
                break;
            }
        }
        assertTrue(f);
    }
    @AfterEach
    public void star(){
        driver.quit();
        driver = null;
    }
}
